define(['observable', 'jquery', 'knockout', 'd3', 'css!svg'], (Observable, $, ko, d3, css) ->
  (elem) ->
    dataO = Observable.makeObservableArray(@dataId)
    dataO.subscribe( (data) =>
      idxf = (idx) -> (d) -> d[idx]
      xf = idxf(0)
      yf = idxf(1)
      m =
        t: 4
        r: 4
        b: 15
        l: 30
      w = $(elem).width()
      h = $(elem).height()
    
      xScale   = d3.scale.linear()
                   .domain([d3.min(data, xf), d3.max(data, xf)])
                   .range([m.l, w - m.r])
      yScale   = d3.scale.linear()
                   .domain([d3.min(data, yf), d3.max(data, yf)])
                   .range([h - m.b, m.t])

      line = d3.svg.line()
          .x( (d) -> xScale(xf(d)) )
          .y( (d) -> yScale(yf(d)) )

      svg = d3.select(elem)
      g = svg.selectAll('path').data([data])
      g.transition().attr('d', line)
      g.enter().append('path').attr('d', line)

      xAxis = d3.svg.axis()
          .scale(xScale)
          .tickSize(-h, 0, 0)
      svg.append('svg:g')
          .attr('class', 'x axis')
          .attr("transform", "translate(0, #{ h - m.b + 2 })")
          .call(xAxis)

      yAxis = d3.svg.axis()
          .scale(yScale)
          .orient('left')
          .tickSize(-w, 0, 0)
      svg.append('svg:g')
          .attr('class', 'y axis')
          .attr("transform", "translate(#{ m.l - 2 })")
          .call(yAxis)
    )
    dataO(@dataInit)
)