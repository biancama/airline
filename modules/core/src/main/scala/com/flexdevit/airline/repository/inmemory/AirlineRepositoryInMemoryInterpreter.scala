package com.flexdevit.airline.repository.inmemory

import cats.Applicative
import cats.implicits.catsSyntaxApplicativeId
import com.flexdevit.airline.domain.airline.{ AirlineRepositoryAlgebra }
import com.flexdevit.airline.domain.airline.airline.Airline

import scala.collection.concurrent.TrieMap
import scala.util.Random

class AirlineRepositoryInMemoryInterpreter[F[_]: Applicative] extends AirlineRepositoryAlgebra[F] {

  private val cache = new TrieMap[Long, Airline]
  override def create(airline: Airline): F[Airline] = {
    val toSave = airline.copy(id = Some(airline.id.getOrElse(Random.nextLong(Long.MaxValue))))
    cache.put(toSave.id.get, toSave)
    toSave.pure[F]
  }

  override def get(id: Long): F[Option[Airline]] = cache.get(id).pure[F]

  override def delete(id: Long): F[Option[Airline]] = cache.remove(id).pure[F]

  override def findAll(): F[List[Airline]] = cache.values.toList.pure
}
