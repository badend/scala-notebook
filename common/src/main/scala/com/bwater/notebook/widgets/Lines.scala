package com.bwater.notebook.widgets

import com.bwater.notebook._

case class Lines(data: Seq[(Double, Double)]) extends Widget with SVG {
  val points =
    for (pt <- data)
    yield pt._1 + "," + pt._2

  lazy val toHtml =
    <svg width={ width } height={ height } version="1.1"
         viewBox={ viewbox }
         xmlns="http://www.w3.org/2000/svg">
    <g transform="scale(1, -1)"> {
      data.map { case(x, y) =>
          <polyline points={ points.mkString(" ") } 
           stroke-width={ (sz / 50).toString }
           stroke={ color } fill="none" />
      }
    } </g> </svg>
}
