package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonAST.{JValue, JArray, JInt}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.DefaultFormats


case class Plot(data: Seq[(Double,Double)]) extends Widget with D3 {
  private[this] val dataConnection = JSBus.createConnection
  val currentData = dataConnection biMap JsonCodec.pairSeq

  lazy val toHtml =
    <svg width={ width.toString } height={ height.toString }
         xmlns="http://www.w3.org/2000/svg" version="1.1">
    {
      scopedScript("""
require(['observable','knockout','d3'], function(Observable, ko, d3) {
  var dataO = Observable.makeObservableArray(dataId);
  dataO.subscribe(function(data) {
    var idxf = function(idx) { return function(d) { return d[idx]; } };
    var xf = idxf(0);
    var yf = idxf(1);
    var xScale   = d3.scale.linear()
                  .domain([d3.min(data, xf), d3.max(data, xf)])
                  .range([0, width]);
    var yScale   = d3.scale.linear()
                  .domain([d3.min(data, yf), d3.max(data, yf)])
                  .range([height, 0]);

    var line = d3.svg.line()
        .x(function(d) { return xScale(xf(d)); })
        .y(function(d) { return yScale(yf(d)); });

    var svg = d3.select(this);
    var g = svg.selectAll('path').data([data]);
    g.transition().attr('d', line);
    g.enter().append('path')
        .attr('d', line)
        .attr('fill', 'none')
        .attr('stroke', color)
        .attr('stroke-width', 4);
  }, this); // `this` is the parent svg element
  dataO(dataInit);
});
""",
        ("dataId" -> dataConnection.id) ~
        ("dataInit" -> JsonCodec.pairSeq.decode(data)) ~
        ("width" -> width) ~
        ("height" -> height) ~
        ("color" -> color)
      )
    } </svg>
}
