package com.flexdevit.airline.routes

import cats.effect.IO
import com.flexdevit.airline.AirlineArbitraries
import com.flexdevit.airline.domain.airline.AirlineService
import com.flexdevit.airline.domain.airline.airline.Airline

import org.http4s.Method._
import org.http4s._
import org.http4s.client.dsl.io._
import org.http4s.syntax.literals._
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.{reset, when}
import org.scalatestplus.mockito.MockitoSugar.mock
import org.typelevel.ci.CIString
import suite.HttpSuite
import weaver.scalacheck.CheckConfig

object  AirlineRoutesSuite extends HttpSuite with AirlineArbitraries {

  override def checkConfig: CheckConfig = CheckConfig(1, 0, 1, 1, None)
  test("findAllAirlines returns 200 and proper body") {
    forall(airlines.arbitrary) { airList =>
      val airlineService = mock[AirlineService[IO]]
      when(airlineService.findAll).thenReturn(IO.pure(airList))
      val req = GET(uri"/airlines")
      val routes = AirlineRoutes[IO](airlineService).routes
      expectHttpBodyAndStatus(routes, req)(airList, Status.Ok)
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

