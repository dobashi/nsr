package com.lavans.nsr

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.config.ConfigFactory
import org.json4s.DefaultFormats
import org.json4s.native.JsonParser

import scala.io.Source

/**
  * Created by ten on 17/07/31.
  */
object ApiRequester {
  val log = org.log4s.getLogger
  val config = ConfigFactory.load()
  val appId = config.getString("applicationId")
  val ENDPOINT = "https://app.rakuten.co.jp/services/api/BooksCD/Search/20170404?"
  val GENRE_CD = "002"
  val param = ApiParams(
    format = "json",
    booksGenreId = GENRE_CD,
    applicationId = appId
  ).toParam
  val url = ENDPOINT + param

  /**
    * JANコードからデータを取得
    *
    * @param jan
    * @return
    */
  def get(jan: String): String = {
    val uri = url + s"jan=$jan"
    log.info(uri)
    Source.fromURL(uri).mkString
  }
}

case class ApiParams(format: String, booksGenreId: String, applicationId: String) {
  def toParam = s"format=$format&booksGenreId=$booksGenreId&applicationId=$applicationId&"
}

