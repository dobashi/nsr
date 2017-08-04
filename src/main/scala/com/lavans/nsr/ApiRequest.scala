package com.lavans.nsr

import com.typesafe.config.ConfigFactory

import scala.io.Source

case class ApiRequest(format: String, booksGenreId: String, applicationId: String) {
  def toParam = s"format=$format&booksGenreId=$booksGenreId&applicationId=$applicationId&"
}

object ApiRequest {
  private val log = org.log4s.getLogger
  private val config = ConfigFactory.load()
  private val appId = config.getString("applicationId")
  private val ENDPOINT = "https://app.rakuten.co.jp/services/api/BooksCD/Search/20170404?"
  private val GENRE_CD = "002"
  // format json, ジャンルID=002でリクエストパラメータを作成
  private val param = ApiRequest(
    format = "json",
    booksGenreId = GENRE_CD,
    applicationId = appId
  ).toParam
  private val url = ENDPOINT + param

  /**
    * JANコードからデータを取得
    *
    * @param jan
    * @return
    */
  def get(jan: String): String = {
    val uri = url + s"jan=$jan"
    log.info(uri)
    Thread.sleep(1000)
    Source.fromURL(uri).mkString
  }
}




