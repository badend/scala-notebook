define(['observable','knockout','d3', 'css!svg'], (Observable, ko, d3) ->
  (elem) ->
    svg = d3.select(elem)
    w = Number(svg.attr('width'))
    h = Number(svg.attr('height'))

    dataO = Observable.makeObservableArray(@dataId)

    dataO.subscribe( (data) ->
      xScale    = d3.scale.linear()
                    .domain([0, data.length - 1])
                    .range([0, w]);
      yScale    = d3.scale.linear()
                    .domain([d3.min(data), d3.max(data)])
                    .range([h, 0]);

      line = d3.svg.line()
          .x( (d, i) -> xScale(i) )
          .y(yScale)

      g = svg.selectAll('path').data([data])
      g.transition().attr('d', line)
      g.enter().append('path')
          .attr('d', line)
    )
    dataO(@dataInit)
)