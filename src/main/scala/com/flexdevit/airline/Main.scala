package com.flexdevit.airline

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  val run = AirlineServer.run[IO]
}
