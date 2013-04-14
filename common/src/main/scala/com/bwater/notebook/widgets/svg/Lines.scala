package com.bwater.notebook.widgets.svg

import com.bwater.notebook._, widgets._

case class Lines(data: Seq[Double]) extends Widget with SVG {
  val points =
    for ((y,x) <- data.zipWithIndex)
    yield x + "," + y

  lazy val toHtml =
    <svg width={ width.toString } height={ height.toString } version="1.1"
         viewBox={ viewbox }
         xmlns="http://www.w3.org/2000/svg">
    <g transform="scale(1, -1)">
      <polyline points={ points.mkString(" ") }
                stroke-width={ (sz * 2).toString }
                stroke={ color } fill="none" />
    </g> </svg>
}
