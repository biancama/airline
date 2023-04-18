package com.flexdevit.airline.modules

import com.flexdevit.airline.domain.airline.AirlineRepositoryAlgebra

object Repositories {
  def make[F[_]](airlineRepositoryAlgebra: AirlineRepositoryAlgebra[F]): Repositories[F] =
    new Repositories[F](airlineRepositoryAlgebra) {}
}
sealed abstract class Repositories[F[_]] private (
    val airlineRepositoryAlgebra: AirlineRepositoryAlgebra[F]
)
