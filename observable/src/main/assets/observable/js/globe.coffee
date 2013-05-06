define([
    'observable'
    'knockout'
    'd3'
    'js!static/topojson.js'
], (Observable, ko, d3, tj) ->
  (elem) ->
    canvas = d3.select(elem)

    w = Number(canvas.attr('width'))
    h = Number(canvas.attr('height'))

    projection = d3.geo.orthographic()
      .scale(w / 2.2)
      .translate([w / 2, h / 2])
      .clipAngle(90)

    c = canvas.node().getContext("2d")

    path = d3.geo.path()
      .projection(projection)
      .context(c)

    d3.json("/js/globe.json", (error, world) =>
      globe = { type: "Sphere" }
      land = topojson.object(world, world.objects.land)
      borders = topojson.mesh(world, world.objects.countries, (a, b) -> a.id isnt b.id)

      dataO = Observable.makeObservable(@dataId)
      dataO.subscribe( (data) =>
        d3.transition()
            .duration(1250)
            .tween("rotate", () ->
              r = d3.interpolate(projection.rotate(), ([-1*data[1], -1*data[0]]))
              (t) ->
                projection.rotate(r(t))
                c.clearRect(0, 0, w, h)

                c.fillStyle = "#bbb"
                c.beginPath()
                path(land)
                c.fill()

                c.strokeStyle = "#fff"
                c.lineWidth = .5
                c.beginPath()
                path(borders)
                c.stroke()

                c.strokeStyle = "#53A0C6"
                c.lineWidth = 1
                c.beginPath()
                path(globe)
                c.stroke()

                c.fillStyle = "#53A0C6"
                c.beginPath()
                c.arc(w/2, h/2, 2, 0, 2 * Math.PI, true)
                c.fill()
            )
      )
      dataO(@dataInit)
    )
)