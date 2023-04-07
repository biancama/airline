package com.flexdevit.airline

import cats.effect.{IOApp}

object Main extends IOApp.Simple {
  val run = AirlineServer.run
}
