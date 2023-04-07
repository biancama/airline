package com.flexdevit.airline.routes

import cats.effect.IO
import org.http4s.Method._
import org.http4s._
import org.http4s.client.dsl.io._
import org.http4s.syntax.literals._
import suite.HttpSuite
import io.circe._, io.circe.generic.semiauto._
object  HealthRoutesSuite extends HttpSuite {

  test("Health Route returns 200") {
    val req = GET(uri"/healthcheck")
    val routes = HealthRoutes[IO]().routes
    expectHttpStatus(routes, req)(Status.Ok)
  }
}
