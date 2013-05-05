package com.bwater.notebook.widgets.d3

import com.bwater.notebook._, widgets._

import net.liftweb.json.JsonDSL._

case class Plot(plottable: Plottable,
                width: Int = 800,
                height: Int = 200,
                line: Boolean = true,
                points: Boolean = false
) extends Widget {
  private[this] val dataConnection = JSBus.createConnection
  lazy val currentData = dataConnection biMap JsonCodec.pairSeq

  lazy val toHtml =
    <svg class="d3 plot" width={ width.toString } height={ height.toString }
         xmlns="http://www.w3.org/2000/svg" version="1.1">
    {
      scopedScript(
        "require('js/plot', function(f) { f.call(data, this); });",
        ("dataId" -> dataConnection.id) ~
        ("dataInit" -> JsonCodec.pairSeq.decode(plottable.data)) ~
        ("config" -> (
          ("line" -> line) ~
          ("points" -> points)
        ))
      )
    } </svg>

  def apply(plottable: Plottable) = currentData <-- Connection.just(plottable.data)
  // explicit plottables so we can do things like Plot.f(math.sin)
  def f(func: (Double => Double)) = this(func)
  def f(func: (Double => Double), min: Double, max: Double) =
    this(BoundedFunction(func, min, max))
}

object Plot {
  def f(func: (Double => Double)) = Plot(func)
  def f(func: (Double => Double), min: Double, max: Double) =
    Plot(BoundedFunction(func, min, max))
}
