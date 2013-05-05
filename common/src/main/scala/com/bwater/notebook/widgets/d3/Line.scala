package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonAST.{JValue, JArray, JInt}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.DefaultFormats

case class Line(data: Seq[Double]) extends Widget {
  private[this] val dataConnection = JSBus.createConnection
  lazy val currentData = dataConnection biMap JsonCodec.doubleSeq

  lazy val toHtml =
    <svg class="d3 line" width="800" height="200"
         xmlns="http://www.w3.org/2000/svg" version="1.1">
    {
      scopedScript(
        "require('js/line', function(f) { f.call(data, this); });",
        ("dataId" -> dataConnection.id) ~
        ("dataInit" -> data)
      )
    } </svg>
}
