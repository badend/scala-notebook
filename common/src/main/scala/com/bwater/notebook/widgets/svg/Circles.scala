package com.bwater.notebook.widgets.svg

import com.bwater.notebook._, widgets._

case class Circles(data: Seq[(Double, Double)]) extends Widget with SVG {

  lazy val toHtml =
    <svg width={ width } height={ height } version="1.1"
         viewBox={ viewbox }
         xmlns="http://www.w3.org/2000/svg">
    <g transform="scale(1, -1)"> {
      data.map { case(x, y) =>
          <circle cx={ x.toString } cy={ y.toString } 
                  r={ (sz / 100).toString }
                  fill={color} />
      }
    } </g> </svg>
}
