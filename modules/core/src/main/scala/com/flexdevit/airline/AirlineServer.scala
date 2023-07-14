package com.flexdevit.airline

import cats.effect.{IO, Resource}
import com.flexdevit.airline.config.Config
import com.flexdevit.airline.config.types.AppConfig
import com.flexdevit.airline.modules.{HttpApi, Repositories, Services}
import com.flexdevit.airline.repository.doobie.AirlineRepositoryPostgresInterpreter
import com.flexdevit.airline.resources.{AppResources, MkHttpServer}
import doobie.util.ExecutionContexts
import org.http4s.server.Server
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
object AirlineServer {
  implicit val logger = Slf4jLogger.getLogger[IO]

  def createServer: Resource[IO, Server] = for {
    config: AppConfig <- Resource.eval(Config.load[IO])
    _ <-  Resource.eval(Logger[IO].info(s"Loaded config $config"))
    connEc <- ExecutionContexts.fixedThreadPool[IO](config.postgreSQLConfig.connections.poolSize)
    res <- AppResources.make[IO](config, connEc)
    _ <- Resource.eval(config.postgreSQLConfig.initializeDB[IO])
    repositories = Repositories.make[IO](new AirlineRepositoryPostgresInterpreter(res.xa))
    services = Services.make[IO](repositories)
    api = HttpApi.make[IO](services)
    server <- MkHttpServer[IO].newEmber(config.httpServerConfig, api.httpApp)
  } yield server


  def run: IO[Unit] = {
    createServer.use(_ => IO.never)
  }
}
