package com.flexdevit.airline.domain.airline

import com.flexdevit.airline.domain.airline.airline.Airline

trait AirlineRepositoryAlgebra[F[_]] {
  def create(airline: Airline): F[Airline]

  def get(id: Long): F[Option[Airline]]

  def findAll(): F[List[Airline]]

  def delete(id: Long): F[Option[Airline]]

}
