package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonDSL._

case class Globe(coords: (Double, Double)) extends Widget {
  private[this] val dataConnection = JSBus.createConnection
  lazy val currentData = dataConnection biMap JsonCodec.pair

  lazy val toHtml =
    <canvas width="400" height="400">
    {
      scopedScript(
        "require('js/globe', function(f) { f.call(data, this); });",
        ("dataId" -> dataConnection.id) ~
        ("dataInit" -> JsonCodec.pair.decode(coords))
      )
    } </canvas>

  def apply(coords: (Double, Double)) = currentData <-- Connection.just(coords)
}
