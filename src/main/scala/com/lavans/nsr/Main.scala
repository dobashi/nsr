package com.lavans.nsr

import java.io.PrintWriter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.io.Source

/**
  * Created by ten on 17/07/31.
  */
object Main {
  val om = new ObjectMapper()
  om.registerModule(DefaultScalaModule)
  val file = "src/main/resources/datafile.csv"
  val outputFile = "src/main/resources/result.csv"

  def main(args: Array[String]): Unit = {
    val log = org.log4s.getLogger
    using(Source.fromFile(file)) { in =>
      using(new PrintWriter(outputFile)) { out =>
        in.mkString.split("\n")
          .map(ApiRequester.get)
          .flatMap(parse(_).map(_.toDataString))
          .map(out.println)
      }
    }
  }

  def using[A <% {def close() : Unit}](s: A)(f: A => Any) {
    try f(s) finally s.close()
  }

  def parse(json: String): Option[Item] ={
    val resp = om.readValue(json, classOf[ApiResponse])
    if(resp.count>0) resp.Items(0).get("Item") else None
  }
}

case class ApiResponse(
  count: Int, page: Int, first: Int, last: Int, hits: Int, carrier: Int, pageCount: Int, Items: List[Map[String,Item]], GenreInformation:List[Any]
)

@JsonIgnoreProperties(ignoreUnknown=true)
case class Item(
  title: String,
  titleKana: String,
  artistName: String,
  artistNameKana: String,
  label: String,
  jan: String,
  makerCode: String
){
  def toDataString = {
    s"$jan, $makerCode, $artistName, $title"
  }
}

