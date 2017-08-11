package com.lavans.nsr

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

case class ApiResponse(
  count: Int, page: Int, first: Int, last: Int, hits: Int, carrier: Int, pageCount: Int, Items: List[Map[String,Item]], GenreInformation:List[Any]
) {
  def firstItem: Item = if (count > 0) Items(0).get("Item").get else ERROR
  val ERROR:Item = Item(jan="ERROR")
}

object ApiResponse {
  val om = new ObjectMapper()
  om.registerModule(DefaultScalaModule)
  def parse(json: String): ApiResponse = om.readValue(json, classOf[ApiResponse])
}

@JsonIgnoreProperties(ignoreUnknown=true)
case class Item(
  title: String="",
  titleKana: String="",
  artistName: String="",
  artistNameKana: String="",
  label: String="",
  jan: String="",
  makerCode: String=""
){
  def toDataString = {
    s"$jan, $makerCode, $artistName, $title"
  }
}

