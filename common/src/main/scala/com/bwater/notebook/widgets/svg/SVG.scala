package com.bwater.notebook.widgets.svg

import com.bwater.notebook._
import widgets._

trait SVG {
  def data: Seq[Double]

  val min = data.min
  val max = data.max

  val viewbox = Seq(0, -max, data.length, max - min).mkString(" ")

  val width = 800
  val height = 200

  val sz = math.max(data.length.toDouble / width,
                    (max - min) / height) * 5

  val color = "#53A0C6"
}
