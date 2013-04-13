package com.bwater.notebook.widgets.svg

import com.bwater.notebook._
import widgets._

trait SVG {
  def data: Seq[(Double, Double)]
  val (d1, d2) = data.unzip

  val (min1, min2) = (d1.min, d2.min)
  val (max1, max2) = (d1.max, d2.max)
  val sz = math.max(max1 - min1, max2 - min2)
  val viewbox = Seq(min1, -1*max2, max1 - min1, max2 - min2).mkString(" ")

  val width = "400px"
  val height = "200px"

  val color = "#53A0C6"
}
