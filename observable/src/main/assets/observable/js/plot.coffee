define(['observable', 'knockout', 'd3', 'css!svg'], (Observable, ko, d3, css) ->
  (elem) ->
    idxf = (idx) -> (d) -> d[idx]
    xf = idxf(0)
    yf = idxf(1)
    m =
      t: 4
      r: 4
      b: 15
      l: 30

    svg = d3.select(elem)

    w = Number(svg.attr('width'))
    h = Number(svg.attr('height'))

    svg.append('svg:g')
        .attr('class', 'x axis')
        .attr("transform", "translate(0, #{ h - m.b + 2 })")

    svg.append('svg:g')
        .attr('class', 'y axis')
        .attr("transform", "translate(#{ m.l - 2 })")

    dataO = Observable.makeObservableArray(@dataId)

    dataO.subscribe( (data) =>
      xScale   = d3.scale.linear()
                   .domain([d3.min(data, xf), d3.max(data, xf)])
                   .range([m.l, w - m.r])
      yScale   = d3.scale.linear()
                   .domain([d3.min(data, yf), d3.max(data, yf)])
                   .range([h - m.b, m.t])

      if @config.line
        line = d3.svg.line()
          .x( (d) -> xScale(xf(d)) )
          .y( (d) -> yScale(yf(d)) )

        l = svg.selectAll('path.dataline').data([data])
        l.transition().attr('d', line)
        l.enter().append('path')
          .attr('class', 'dataline')
          .attr("d", line)


      if @config.points
        point = (d) -> "translate(" + xScale(xf(d)) + "," + yScale(yf(d)) + ")"
        p = svg.selectAll('path.datapoint').data(data)
        p.transition()
          .attr("transform", point)
        p.enter().append('path')
          .attr('class', 'datapoint')
          .attr("transform", point)
          .attr("d", d3.svg.symbol())
        p.exit().remove()

      xAxis = d3.svg.axis()
        .scale(xScale)
        .tickSize(-h, 0, 0)

      svg.select('g.x.axis').transition().call(xAxis)

      yAxis = d3.svg.axis()
        .scale(yScale)
        .orient('left')
        .tickSize(-w, 0, 0)
      svg.select('g.y.axis').transition().call(yAxis)
    )
    dataO(@dataInit)
)