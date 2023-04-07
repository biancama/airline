package com.flexdevit.airline.config

import cats.effect.kernel.Async
import cats.implicits.catsSyntaxTuple2Parallel
import ciris.{ConfigValue, env}
import com.comcast.ip4s.{Host, IpLiteralSyntax, Port}
import com.flexdevit.airline.config.AppEnvironment.{Production, Test}
import com.flexdevit.airline.config.types.{AppConfig, HttpServerConfig}

object Config {

  def load[F[_]: Async]: F[AppConfig] =
    env("AL_APP_ENV")
      .as[AppEnvironment]
      .flatMap{
        case Test =>
          default[F]
        case Production =>
          default[F]
      }.load[F]

  private def default[F[_]]: ConfigValue[F, AppConfig] = (
    env("AL_HOST").as[String],
    env("AL_PORT").as[Int]
  ).parMapN {(h, p) =>
    AppConfig(
      HttpServerConfig(
        host = Host.fromString(h).getOrElse(host"0.0.0.0"),
        port = Port.fromInt(p).getOrElse(port"8080")
      )
    )
  }
}
