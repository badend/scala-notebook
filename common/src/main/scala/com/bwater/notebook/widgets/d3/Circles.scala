package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonAST.{JValue, JArray, JInt}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.DefaultFormats

case class Circles(data: Seq[Double]) extends Widget with D3 {
  private[this] val dataConnection = JSBus.createConnection
  lazy val currentData = dataConnection biMap JsonCodec.doubleSeq

  lazy val toHtml =
    <svg class="d3 circles" width={ width.toString } height={ height.toString }
         xmlns="http://www.w3.org/2000/svg" version="1.1">
    {
      scopedScript(
        "require('js/circles', function(f) { f.call(data, this); });",
        ("dataId" -> dataConnection.id) ~
        ("dataInit" -> data)
      )
    } </svg>
}
