commands -> value object (model)
entities -> value object (model)

model: aggregates, entities, value objects

- command : [value object]
- event : [value object]
- entity : [value object], [command handler], [event sourcing handler] [event emitter]
- command handler : command
- event sourcing handler : event
- (event emitter : [event])
- aggregate : [entities]

domain service : [value object], [aggregate / projection], [event emitter]

- model : [aggregate]

application service : ?

infrastructure : ports
