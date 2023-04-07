package com.flexdevit.airline

import cats.effect.IO
import cats.effect.std.Supervisor
import com.flexdevit.airline.config.Config
import com.flexdevit.airline.modules.HttpApi
import com.flexdevit.airline.resources.MkHttpServer
import org.typelevel.log4cats.slf4j.Slf4jLogger

object AirlineServer {
  implicit val logger = Slf4jLogger.getLogger[IO]

  def run: IO[Unit] = {

    Config.load[IO].flatMap { config =>
      Supervisor[IO].use { implicit sp =>
        val api      = HttpApi.make[IO]
        MkHttpServer[IO].newEmber(config.httpServerConfig, api.httpApp).useForever
      }
    }
  }
}
