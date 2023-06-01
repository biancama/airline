package com.flexdevit.airline.routes

import cats.data.EitherT
import cats.effect.IO
import cats.implicits.{catsSyntaxApplicativeId, catsSyntaxEitherId}
import com.flexdevit.airline.AirlineArbitraries
import com.flexdevit.airline.domain.AirlineNotFoundError
import com.flexdevit.airline.domain.airline.AirlineService
import com.flexdevit.airline.domain.airline.airline.Airline
import org.http4s.Method._
import org.http4s._
import org.http4s.client.dsl.io._
import org.http4s.syntax.literals._
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar.mock
import org.typelevel.ci.CIString
import suite.HttpSuite
import weaver.scalacheck.CheckConfig

object  AirlineRoutesSuite extends HttpSuite with AirlineArbitraries {

  override def checkConfig: CheckConfig = CheckConfig(1, 0, 1, 1, None)
  test("find All Airlines returns 200 and proper body") {
    forall(airlines.arbitrary) { airList =>
      val airlineService = mock[AirlineService[IO]]
      when(airlineService.findAll).thenReturn(IO.pure(airList))
      val req = GET(uri"/airlines")
      val routes = AirlineRoutes[IO](airlineService).routes
      expectHttpBodyAndStatus(routes, req)(airList, Status.Ok)
    }
  }

  test("find by id returns 200 and proper body") {
    forall(airlineCreated.arbitrary) { air =>
      val airlineService = mock[AirlineService[IO]]
      when(airlineService.get(air.id.get)).thenReturn(EitherT[IO, AirlineNotFoundError.type, Airline](air.asRight[AirlineNotFoundError.type].pure[IO]))
      val req = GET(Uri.unsafeFromString(s"/airlines/${air.id.get}"))
      val routes = AirlineRoutes[IO](airlineService).routes
      expectHttpBodyAndStatus(routes, req)(air, Status.Ok)
    }
  }

  test("find by id returns 404 if airline is not found") {
    forall(airlineCreated.arbitrary) { air =>
      val airlineService = mock[AirlineService[IO]]
      when(airlineService.get(air.id.get)).thenReturn(EitherT[IO, AirlineNotFoundError.type, Airline](AirlineNotFoundError.asLeft[Airline].pure[IO]))
      val req = GET(Uri.unsafeFromString(s"/airlines/${air.id.get}"))
      val routes = AirlineRoutes[IO](airlineService).routes
      expectHttpStatus(routes, req)(Status.NotFound)
    }
  }

  test("find by id error if service is failing") {
    forall(airlineCreated.arbitrary) { air =>
      val airlineService = mock[AirlineService[IO]]
      when(airlineService.get(air.id.get)).thenThrow(new RuntimeException())
      val req = GET(Uri.unsafeFromString(s"/airlines/${air.id.get}"))
      val routes = AirlineRoutes[IO](airlineService).routes
      expectHttpFailure(routes, req)
    }
  }

  test("delete by id returns 200 and proper body") {
    forall(airlineCreated.arbitrary) { air =>
      val airlineService = mock[AirlineService[IO]]
      when(airlineService.delete(air.id.get)).thenReturn(EitherT[IO, AirlineNotFoundError.type, Airline](air.asRight[AirlineNotFoundError.type].pure[IO]))
      val req = DELETE(Uri.unsafeFromString(s"/airlines/${air.id.get}"))
      val routes = AirlineRoutes[IO](airlineService).routes
      expectHttpBodyAndStatus(routes, req)(air, Status.Ok)
    }
  }

  test("create an airline returns 200 and id in location header") {
    import org.http4s.circe.CirceEntityEncoder._
    forall(airlineTupled.arbitrary) { case (air, id) =>
      val airlineService = mock[AirlineService[IO]]

      when(airlineService.create(air.toDomain)).thenReturn(IO.pure(air.toDomain.copy(id = Some(id))))
      val req = POST(air, uri"/airlines")
      val routes = AirlineRoutes[IO](airlineService).routes
      expectHttpStatusAndHeadersContains(routes, req)(Status.Created, Header.Raw(CIString("location"), s"/airlines/$id"))
    }
  }

}

