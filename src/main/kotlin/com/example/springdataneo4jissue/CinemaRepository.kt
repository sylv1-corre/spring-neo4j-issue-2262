package com.example.springdataneo4jissue

import org.springframework.data.neo4j.repository.Neo4jRepository

interface CinemaRepository: Neo4jRepository<Cinema, String>