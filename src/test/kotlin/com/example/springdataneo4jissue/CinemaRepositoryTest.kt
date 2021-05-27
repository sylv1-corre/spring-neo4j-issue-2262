package com.example.springdataneo4jissue

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.harness.TestServerBuilders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.config.AbstractNeo4jConfig
import org.springframework.data.neo4j.core.convert.Neo4jConversions
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.EnableTransactionManagement


@ExtendWith(SpringExtension::class)
class CinemaRepositoryTest {
    @Autowired
    lateinit var cinemaRepository : CinemaRepository

    @Configuration
    @EnableNeo4jRepositories(considerNestedRepositories = true)
    @EnableTransactionManagement
    internal class Config : AbstractNeo4jConfig() {
        @Bean
        override fun driver(): Driver {
            return GraphDatabase.driver("${embeddedDatabaseServer.boltURI()}", AuthTokens.none())
        }

        @Bean
        override fun neo4jMappingContext(@Autowired neo4JConversions: Neo4jConversions): Neo4jMappingContext {
            val f = Neo4jMappingContext(neo4JConversions)
            f.setInitialEntitySet(setOf(Cinema::class.java, Movie::class.java, AnimationMovie::class.java))
            return f
        }

        override fun getMappingBasePackages(): Collection<String> {
            return setOf(Cinema::class.java.getPackage().name)
        }
    }


    companion object {
        val embeddedDatabaseServer = TestServerBuilders.newInProcessBuilder()
            .withFixture("""
                CREATE (:Movie:AnimationMovie {id: 'movie001', name: 'movie-001', studio: 'Pixar'})<-[:Plays]-(:Cinema {id:'cine-01', name: 'GrandRex'})
            """.trimIndent()).newServer()
    }

    @Test
    fun repositoryTest() {
        val cinemas = cinemaRepository.findAll()

        assert(cinemas.size == 1)
    }
}