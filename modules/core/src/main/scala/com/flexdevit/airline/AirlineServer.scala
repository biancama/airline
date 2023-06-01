package com.flexdevit.airline

import cats.effect.IO
import cats.effect.std.Supervisor
import com.flexdevit.airline.config.Config
import com.flexdevit.airline.modules.{HttpApi, Repositories, Services}
import com.flexdevit.airline.repository.inmemory.AirlineRepositoryInMemoryInterpreter
import com.flexdevit.airline.resources.{AppResources, MkHttpServer}
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object AirlineServer {
  implicit val logger = Slf4jLogger.getLogger[IO]

  def run: IO[Unit] = {

    Config.load[IO].flatMap { config =>
      Logger[IO].info(s"Loaded config $config") >>
      Supervisor[IO].use { implicit sp =>
        AppResources
          .make[IO](config)
          .evalMap { res =>
            val repositories = Repositories.make[IO](new AirlineRepositoryInMemoryInterpreter)
            val services = Services.make[IO](repositories)
            val api = HttpApi.make[IO](services)
            IO(api)
          }
          .flatMap(api =>
            MkHttpServer[IO].newEmber(config.httpServerConfig, api.httpApp)
        ).useForever
      }
    }
  }
}
