package com.flexdevit.airline.routes

import cats.effect.Concurrent
import cats.syntax.functor._
import cats.syntax.flatMap._
import com.flexdevit.airline.domain.AirlineNotFoundError
import com.flexdevit.airline.domain.airline.airline._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import org.http4s._
import org.http4s.circe._
import org.http4s.headers.Location
import com.flexdevit.airline.domain.airline.{AirlineService}
import io.circe.syntax._
import org.http4s.circe.CirceEntityCodec._

final case class AirlineRoutes[F[_]: Concurrent](airlineService: AirlineService[F]) extends Http4sDsl[F] {
  private val prefixPath = "/airlines"

  private def createAirline(airlineService: AirlineService[F]): ReqToRes[F] = {
    case req @ POST -> Root =>
      for {
        createAirLine <- req.as[CreateAirlineParam]
        airline       <- airlineService.create(createAirLine.toDomain)
        location = s"$prefixPath/${airline.id.get}"
        res <- Created.headers(Headers(Location(Uri.unsafeFromString(location))))
      } yield res
  }

  private def findAllAirlines(airlineService: AirlineService[F]): ReqToRes[F] = {
    case GET -> Root =>
      for {
        airlines <- airlineService.findAll
        res      <- Ok(airlines.asJson)
      } yield res
  }

  private def findAirlineById(airlineService: AirlineService[F]): ReqToRes[F] = {
    case GET -> Root / LongVar(id) =>
      airlineService.get(id).value flatMap {
        case Left(AirlineNotFoundError) => NotFound("The airline was not found")
        case Right(found)               => Ok(found.asJson)
      }

  }

  private def deleteAirlineById(airlineService: AirlineService[F]): ReqToRes[F] = {
    case DELETE -> Root / LongVar(id) =>
      airlineService.delete(id).value flatMap {
        case Left(AirlineNotFoundError) => NotFound("The airline was not found!")
        case Right(found)               => Ok(found.asJson)
      }

  }

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    findAllAirlines(airlineService)
      .orElse(findAirlineById(airlineService))
      .orElse(createAirline(airlineService))
      .orElse(deleteAirlineById(airlineService))
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
