package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonAST.{JValue, JArray, JInt}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.DefaultFormats

case class Circles(data: Seq[Double]) extends Widget with D3 {
  private[this] val dataConnection = JSBus.createConnection
  val currentData = dataConnection biMap JsonCodec.doubleSeq

  lazy val toHtml =
    <svg width={ width.toString } height={ height.toString }
         xmlns="http://www.w3.org/2000/svg" version="1.1">
    {
      scopedScript("""
require(['observable','knockout','d3'], function(Observable, ko, d3) {
  console.log("going to make " + dataId + " observable");
  var dataO = Observable.makeObservableArray(dataId);
  dataO.subscribe(function(data) {
    var x     = d3.scale.linear()
                  .domain([0, data.length - 1])
                  .range([0, width]);
    var y     = d3.scale.linear()
                  .domain([d3.min(data), d3.max(data)])
                  .range([height, 0]);

    var svg = d3.select(this); // our svg element

    var g = svg.selectAll('circle').data(data);
    g.transition()
      .attr('cx', function(d, i) { return x(i); })
      .attr('cy', y);
    g.enter().append('circle')
        .attr('cx', width)
        .attr('cy', y)
        .attr('r', '5')
        .attr('fill', color)
      .transition()
        .attr('cx', function(d, i) { return x(i); });
    g.exit().remove();
  }, this); // `this` is the parent svg element
  dataO(dataInit);
});
""",
        ("dataId" -> dataConnection.id) ~
        ("dataInit" -> data) ~
        ("width" -> width) ~
        ("height" -> height) ~
        ("color" -> color)
      )
    } </svg>
}
