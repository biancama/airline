package com.flexdevit.airline.domain
package airline

import cats.Functor
import cats.data.EitherT
import com.flexdevit.airline.domain.airline.airline.Airline

class AirlineService[F[_]: Functor](airlineRepository: AirlineRepositoryAlgebra[F]) {

  def create(airline: Airline): F[Airline] = airlineRepository.create(airline)
  def get(id: Long): EitherT[F, AirlineNotFoundError.type, Airline] =
    EitherT.fromOptionF(airlineRepository.get(id), AirlineNotFoundError)
  def findAll: F[List[Airline]] = airlineRepository.findAll()
  def delete(id: Long): EitherT[F, AirlineNotFoundError.type, Airline] =
    EitherT.fromOptionF(airlineRepository.delete(id), AirlineNotFoundError)

}

object AirlineService {
  def make[F[_]: Functor](airlineRepository: AirlineRepositoryAlgebra[F]): AirlineService[F] =
    new AirlineService(airlineRepository)
}
