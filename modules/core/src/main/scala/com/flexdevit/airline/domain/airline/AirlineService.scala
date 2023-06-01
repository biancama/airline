package com.flexdevit.airline.domain
package airline

import cats.Functor
import cats.data.EitherT
import com.flexdevit.airline.domain.airline.airline.Airline

trait AirlineService[F[_]] {
  def create(airline: Airline): F[Airline]
  def get(id: Long): EitherT[F, AirlineNotFoundError.type, Airline]
  def findAll: F[List[Airline]]
  def delete(id: Long): EitherT[F, AirlineNotFoundError.type, Airline]
}
class AirlineServiceImpl[F[_]: Functor](airlineRepository: AirlineRepositoryAlgebra[F]) extends AirlineService[F]{

  def create(airline: Airline): F[Airline] = airlineRepository.create(airline)
  def get(id: Long): EitherT[F, AirlineNotFoundError.type, Airline] =
    EitherT.fromOptionF(airlineRepository.get(id), AirlineNotFoundError)
  def findAll: F[List[Airline]] = airlineRepository.findAll()
  def delete(id: Long): EitherT[F, AirlineNotFoundError.type, Airline] =
    EitherT.fromOptionF(airlineRepository.delete(id), AirlineNotFoundError)

}

object AirlineServiceImpl {
  def make[F[_]: Functor](airlineRepository: AirlineRepositoryAlgebra[F]): AirlineService[F] =
    new AirlineServiceImpl(airlineRepository)
}
