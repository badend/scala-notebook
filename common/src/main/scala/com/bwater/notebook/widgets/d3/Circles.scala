package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonAST.{JValue, JArray, JInt}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.DefaultFormats

case class Circles(data: Seq[(Double, Double)]) extends Widget with svg.SVG {

  lazy val toHtml =
    <svg width={ width } height={ height } version="1.1"
         viewBox={ viewbox }
         class="circles"
         xmlns="http://www.w3.org/2000/svg">
    <g transform="scale(1, -1)"> {
      scopedScript("""
require(['d3'], function(d3) {
  var g = d3.selectAll('svg.circles g');
  g.selectAll('circle')
    .data(data)
  .enter().append('circle')
    .attr('cx', function(d) { return d.x })
    .attr('cy', function(d) { return d.y })
    .attr('r', '%s')
    .attr('fill', '%s')
});
""".format((sz / 100).toString, color), (
        "data" -> (for ((x,y) <- data) yield ("x" -> x) ~ ("y" -> y))
      ))
    } </g> </svg>
}
