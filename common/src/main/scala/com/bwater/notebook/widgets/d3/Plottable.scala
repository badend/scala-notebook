package com.bwater.notebook.widgets.d3

trait Plottable {
  def data: Seq[(Double,Double)]
}

case class BoundedFunction(f: (Double => Double), min: Double, max: Double) extends Plottable {
  lazy val data =
    for (x <- min to max by (max - min) / 1000)
    yield (x, f(x))
}

object Plottable {
  implicit def dataToPlottable(dt: Seq[(Double,Double)]) =
    new Plottable { def data = dt } 

  implicit def functionToPlottable(f: (Double => Double)) =
    BoundedFunction(f, -10d, 10d)
}
  
