package com.flexdevit.airline.modules

import cats.effect.Async
import cats.implicits.toSemigroupKOps
import com.flexdevit.airline.routes.{ AirlineRoutes, HealthRoutes, version }
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import org.http4s.{ HttpApp, HttpRoutes }
import org.http4s.server.middleware.{ AutoSlash, CORS, RequestLogger, ResponseLogger, Timeout }

import scala.concurrent.duration.DurationInt

object HttpApi {
  def make[F[_]: Async](services: Services[F]) = new HttpApi[F](services) {}
}

sealed abstract class HttpApi[F[_]: Async] private (services: Services[F]) {
  private val healthRoutes  = HealthRoutes[F]().routes
  private val airlineRoutes = AirlineRoutes[F](services.airlineService).routes

  private val openRoutes: HttpRoutes[F] = healthRoutes <+> airlineRoutes
  private val routes: HttpRoutes[F] = Router(
    version.v1 -> openRoutes
  )
  private val middleware: HttpRoutes[F] => HttpRoutes[F] = {
    { http: HttpRoutes[F] =>
      AutoSlash(http)
    } andThen { http: HttpRoutes[F] =>
      CORS(http)
    } andThen { http: HttpRoutes[F] =>
      Timeout(60.seconds)(http)
    }
  }
  private val loggers: HttpApp[F] => HttpApp[F] = {
    { http: HttpApp[F] =>
      RequestLogger.httpApp(true, true)(http)
    } andThen { http: HttpApp[F] =>
      ResponseLogger.httpApp(true, true)(http)
    }
  }

  val httpApp = loggers(middleware(routes).orNotFound)
}
