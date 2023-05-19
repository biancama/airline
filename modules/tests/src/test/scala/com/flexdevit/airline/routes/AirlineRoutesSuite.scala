package com.flexdevit.airline.routes

import cats.effect.IO
import com.flexdevit.airline.AirlineArbitraries
import com.flexdevit.airline.domain.airline.{AirlineService}
import org.http4s.Method._
import org.http4s._
import org.http4s.client.dsl.io._
import org.http4s.syntax.literals._
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar.mock
import suite.HttpSuite

object  AirlineRoutesSuite extends HttpSuite with AirlineArbitraries {
  val airlineService = mock[AirlineService[IO]]
  test("findAllAirlines returns 200 and proper body") {
    forall(airlines.arbitrary) { air =>
      when(airlineService.findAll).thenReturn(IO.pure(air))
      val req = GET(uri"/airlines")
      val routes = AirlineRoutes[IO](airlineService).routes
      expectHttpBodyAndStatus(routes, req)(air, Status.Ok)
    }
  }
}

