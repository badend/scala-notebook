define(['observable','knockout','d3'], (Observable, ko, d3) ->
  (elem) ->
    dataO = Observable.makeObservableArray(@dataId)
    dataO.subscribe( (data) =>
      idxf = (idx) -> (d) -> d[idx]
      xf = idxf(0)
      yf = idxf(1)
      xScale   = d3.scale.linear()
                    .domain([d3.min(data, xf), d3.max(data, xf)])
                    .range([0, @width])
      yScale   = d3.scale.linear()
                    .domain([d3.min(data, yf), d3.max(data, yf)])
                    .range([@height, 0])

      line = d3.svg.line()
          .x( (d) -> xScale(xf(d)) )
          .y( (d) -> yScale(yf(d)) )

      svg = d3.select(elem);
      g = svg.selectAll('path').data([data]);
      g.transition().attr('d', line);
      g.enter().append('path')
          .attr('d', line)
          .attr('fill', 'none')
          .attr('stroke', @color)
          .attr('stroke-width', 4);
    )
    dataO(@dataInit)
)