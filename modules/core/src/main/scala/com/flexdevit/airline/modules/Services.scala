package com.flexdevit.airline.modules

import cats.Functor
import com.flexdevit.airline.domain.airline.{AirlineService, AirlineServiceImpl}

object Services {

  def make[F[_]: Functor](repositories: Repositories[F]): Services[F] =
    new Services[F](
      airlineService = AirlineServiceImpl.make[F](repositories.airlineRepositoryAlgebra)
    ) {}
}
sealed abstract class Services[F[_]] private (
    val airlineService: AirlineService[F]
)
