package io.richsan.bookstore.configurations


import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.net.URI


@Configuration
@EnableTransactionManagement
class DataBaseConfiguration : AbstractR2dbcConfiguration()
{

    @Value("\${spring.r2dbc.url}")
    lateinit var url : String

    @Value("\${spring.r2dbc.username}")
    lateinit var userName : String

    @Value("\${spring.r2dbc.password}")
    lateinit var password : String


    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val options : MutableMap<String,String> = HashMap<String,String>();

        options["lock_timeout"] = "10s";
        val urlParsed = URI.create(url.substringAfter(":"))

        return PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host(urlParsed.host)
                .port(urlParsed.port.takeUnless { it == -1 } ?: 5432)
                .username(userName)
                .password(password)
                .database(urlParsed.path.substringAfter("/")) // optional
                .options(options) // optional
                .build())

    }

    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory?): ReactiveTransactionManager? {
        return R2dbcTransactionManager(connectionFactory!!)
    }
}