package com.bwater.notebook.widgets.svg

import com.bwater.notebook._, widgets._

case class Circles(data: Seq[Double]) extends Widget with SVG {

  lazy val toHtml =
    <svg width={ width.toString } height={ height.toString } version="1.1"
         viewBox={ viewbox }
         xmlns="http://www.w3.org/2000/svg">
    <g transform="scale(1, -1)"> {
      (for ((y,x) <- data.zipWithIndex) yield
          <circle cx={ x.toString } cy={ y.toString } 
                  r={ sz.toString }
                  fill={color} />)
    } </g> </svg>
}
