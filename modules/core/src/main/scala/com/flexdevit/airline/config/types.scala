package com.flexdevit.airline.config

import com.comcast.ip4s.{Host, Port}
import eu.timepit.refined.types.all.{NonEmptyString, PosInt, UserPortNumber}

object types {


  case class AppConfig(httpServerConfig: HttpServerConfig, postgreSQLConfig: PostgreSQLConfig)
  case class HttpServerConfig(host: Host, port: Port)

  case class PostgreSQLConfig(
                               driver: NonEmptyString,
                               url: NonEmptyString,
                               user: NonEmptyString,
                               password: NonEmptyString,
                               poolSize: PosInt
                             )
}
