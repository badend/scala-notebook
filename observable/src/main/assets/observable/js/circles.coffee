define(['observable','knockout','d3', 'css!svg'], (Observable, ko, d3, css) ->
  (elem) ->
    svg = d3.select(elem)

    w = Number(svg.attr('width'))
    h = Number(svg.attr('height'))

    dataO = Observable.makeObservableArray(@dataId)

    dataO.subscribe( (data) =>
      xScale    = d3.scale.linear()
                    .domain([0, data.length - 1])
                    .range([0, w])
      yScale    = d3.scale.linear()
                    .domain([d3.min(data), d3.max(data)])
                    .range([h, 0])

      g = svg.selectAll('circle').data(data)
      g.transition()
        .attr('cx', (d, i) -> xScale(i) )
        .attr('cy', yScale);
      g.enter().append('circle')
          .attr('cx', w)
          .attr('cy', yScale)
          .attr('r', '5')
        .transition()
          .attr('cx', (d, i) -> xScale(i) )
      g.exit().remove()
    )
    dataO(@dataInit)
)