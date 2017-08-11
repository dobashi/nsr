package com.lavans.nsr

import java.io.PrintWriter

import scala.io.Source

/**
  * Created by ten on 17/07/31.
  */
object Main {

  val inputfile = "src/main/resources/input.csv"
  val outputFile = "src/main/resources/output.csv"

  def main(args: Array[String]): Unit = {
    using(Source.fromFile(inputfile)) { in =>
      using(new PrintWriter(outputFile)) { out =>
        in.mkString.split("\n")
          .map(ApiRequest.get)
          .map(ApiResponse.parse(_).firstItem)
          .map(_.toDataString)
          .map(out.println)
      }
    }
  }

  def using[A <% {def close() : Unit}](s: A)(f: A => Any) {
    try f(s) finally s.close()
  }
}


