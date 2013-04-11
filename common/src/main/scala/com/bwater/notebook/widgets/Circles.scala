package com.bwater.notebook.widgets

import com.bwater.notebook._

case class Circles(data: Seq[(Double, Double)]) extends Widget {
  val (d1, d2) = data.unzip

  val (min1, min2) = (d1.min, d2.min)
  val (max1, max2) = (d1.max, d2.max)
  val sz = math.max(max1 - min1, max2 - min2)
  val viewbox = Seq(min1, -1*max2, max1 - min1, max2 - min2).mkString(" ")

  lazy val toHtml =
    <svg width="400px" height="200px" version="1.1"
         viewBox={ viewbox }
         xmlns="http://www.w3.org/2000/svg">
    <g transform="scale(1, -1)"> {
      data.map { case(x, y) =>
          <circle cx={ x.toString } cy={ y.toString } 
                  r={ (sz / 100).toString }
          style="fill: #53A0C6"/>
      }
    } </g> </svg>
}
