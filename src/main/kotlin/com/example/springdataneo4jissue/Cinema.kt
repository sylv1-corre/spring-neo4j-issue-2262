package com.example.springdataneo4jissue

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Cinema")
class Cinema(@Id val id: String, val name: String, @Relationship("Plays", direction = Relationship.Direction.OUTGOING) val plays: List<Movie>)