package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonAST.{JValue, JArray, JInt}
import net.liftweb.json.JsonDSL._
import net.liftweb.json.DefaultFormats

case class Circles(data: Seq[Double]) extends Widget with D3 {

  lazy val toHtml =
    <div class="d3_circles">
    {
      scopedScript("""
require(['d3'], function(d3) {
  var w = %d;
  var h = %d;

  var max   = d3.max(data);
  var x     = d3.scale.linear().domain([0, data.length - 1]).range([0, w]);
  var y     = d3.scale.linear().domain([0, max]).range([h, 0]);

  var g = d3.select('div.d3_circles')
    .append('svg:svg')
      .attr('width', w)
      .attr('height', h);

  g.selectAll('circle')
    .data(data)
  .enter().append('circle')
    .attr('cx', function(d, i) { return x(i); })
    .attr('cy', y)
    .attr('r', '2')
    .attr('fill', '%s')
});
""".format(width, height, color),
        ("data" -> data)
      )
    } </div>
}
