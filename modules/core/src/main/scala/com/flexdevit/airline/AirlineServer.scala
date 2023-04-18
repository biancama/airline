package com.flexdevit.airline

import cats.effect.{ IO }
import cats.effect.std.Supervisor
import com.flexdevit.airline.config.Config
import com.flexdevit.airline.modules.{ HttpApi, Repositories, Services }
import com.flexdevit.airline.repository.inmemory.AirlineRepositoryInMemoryInterpreter
import com.flexdevit.airline.resources.MkHttpServer
import org.typelevel.log4cats.slf4j.Slf4jLogger

object AirlineServer {
  implicit val logger = Slf4jLogger.getLogger[IO]

  def run: IO[Unit] = {

    Config.load[IO].flatMap { config =>
      Supervisor[IO].use { implicit sp =>
        val repositories = Repositories.make[IO](new AirlineRepositoryInMemoryInterpreter)
        val services     = Services.make[IO](repositories)
        val api          = HttpApi.make[IO](services)
        MkHttpServer[IO].newEmber(config.httpServerConfig, api.httpApp).useForever
      }
    }
  }
}
