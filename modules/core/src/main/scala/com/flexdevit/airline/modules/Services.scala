package com.flexdevit.airline.modules

import cats.Functor
import com.flexdevit.airline.domain.airline.AirlineService

object Services {

  def make[F[_]: Functor](repositories: Repositories[F]): Services[F] =
    new Services[F](
      airlineService = AirlineService.make[F](repositories.airlineRepositoryAlgebra)
    ) {}
}
sealed abstract class Services[F[_]] private (
    val airlineService: AirlineService[F]
)
