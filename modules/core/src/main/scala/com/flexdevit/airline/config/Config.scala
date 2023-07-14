package com.flexdevit.airline.config

import cats.effect.kernel.Async
import cats.implicits.catsSyntaxTuple7Parallel
import ciris.refined.refTypeConfigDecoder
import ciris.{ConfigValue, env}
import com.comcast.ip4s.{Host, IpLiteralSyntax, Port}
import com.flexdevit.airline.config.AppEnvironment.{Production, Test}
import com.flexdevit.airline.config.types.{AppConfig, DatabaseConfig, DatabaseConnectionsConfig, HttpServerConfig}
import eu.timepit.refined.types.all.{NonEmptyString, PosInt}

object Config {

  def load[F[_]: Async]: F[AppConfig] =
    env("AL_APP_ENV")
      .as[AppEnvironment]
      .flatMap {
        case Test =>
          default[F]
        case Production =>
          default[F]
      }
      .load[F]

  private def default[F[_]]: ConfigValue[F, AppConfig] =
    (
      env("AL_HOST").as[String],
      env("AL_PORT").as[Int],
      env("AL_POSTGRES_DRIVER").as[NonEmptyString],
      env("AL_POSTGRES_URL").as[NonEmptyString],
      env("AL_POSTGRES_USER").as[NonEmptyString],
      env("AL_POSTGRES_PWD").as[NonEmptyString],
      env("AL_POSTGRES_POOL_SIZE").as[PosInt]
    ).parMapN { (h, p, postgresDriver,  postgresUrl, postgresUser, postgresPwd, postgresPoolSize) =>
      AppConfig(
        HttpServerConfig(
          host = Host.fromString(h).getOrElse(host"0.0.0.0"),
          port = Port.fromInt(p).getOrElse(port"8080")
        ),
        DatabaseConfig(
          driver = postgresDriver.value, url = postgresUrl.value, user = postgresUser.value, password = postgresPwd.value, connections = DatabaseConnectionsConfig(postgresPoolSize.value)
        )
      )
    }
}
