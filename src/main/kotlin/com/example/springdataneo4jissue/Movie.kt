package com.example.springdataneo4jissue

import org.springframework.data.neo4j.core.schema.Node

@Node("Movie")
interface Movie {
    val id: String
    val name: String
}