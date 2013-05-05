package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonAST.{JValue, JArray, JInt}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.DefaultFormats


case class Plot(data: Seq[(Double,Double)]) extends Widget {
  private[this] val dataConnection = JSBus.createConnection
  val currentData = dataConnection biMap JsonCodec.pairSeq

  lazy val toHtml =
    <svg class="d3 plot" xmlns="http://www.w3.org/2000/svg" version="1.1">
    {
      scopedScript(
        "require('js/plot', function(f) { f.call(data, this); });",
        ("dataId" -> dataConnection.id) ~
        ("dataInit" -> JsonCodec.pairSeq.decode(data))
      )
    } </svg>
}
