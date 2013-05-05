define(['observable', 'jquery', 'knockout', 'd3', 'css!svg'], (Observable, $, ko, d3, css) ->
  (elem) ->
    dataO = Observable.makeObservableArray(@dataId)
    dataO.subscribe( (data) =>
      idxf = (idx) -> (d) -> d[idx]
      xf = idxf(0)
      yf = idxf(1)
      MGN = 3
      width = $(elem).width()
      height = $(elem).height()
    
      xScale   = d3.scale.linear()
                   .domain([d3.min(data, xf), d3.max(data, xf)])
                   .range([MGN, width - MGN])
      yScale   = d3.scale.linear()
                   .domain([d3.min(data, yf), d3.max(data, yf)])
                   .range([height - MGN, MGN])

      line = d3.svg.line()
          .x( (d) -> xScale(xf(d)) )
          .y( (d) -> yScale(yf(d)) )

      svg = d3.select(elem)
      g = svg.selectAll('path').data([data])
      g.transition().attr('d', line)
      g.enter().append('path').attr('d', line)
    )
    dataO(@dataInit)
)