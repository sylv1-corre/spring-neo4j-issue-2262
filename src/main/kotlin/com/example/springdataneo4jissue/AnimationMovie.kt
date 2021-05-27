package com.example.springdataneo4jissue

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node("AnimationMovie")
class AnimationMovie(@Id override val id: String, override val name: String, val studio: String): Movie