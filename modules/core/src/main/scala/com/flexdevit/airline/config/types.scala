package com.flexdevit.airline.config

import cats.effect.kernel.Sync
import cats.implicits.toFunctorOps
import com.comcast.ip4s.{Host, Port}
import org.flywaydb.core.Flyway
object types {


  case class AppConfig(httpServerConfig: HttpServerConfig, postgreSQLConfig: DatabaseConfig)
  case class HttpServerConfig(host: Host, port: Port)

  case class DatabaseConnectionsConfig(poolSize: Int)

  case class DatabaseConfig(
                             url: String,
                             driver: String,
                             user: String,
                             password: String,
                             connections: DatabaseConnectionsConfig,
                           ) {
    def initializeDB[F[_]: Sync]: F[Unit] =
      Sync[F].delay {
        val fw: Flyway = Flyway.configure().dataSource(url, user, password).load()
        fw.migrate()
      }.as(())
  }
}
