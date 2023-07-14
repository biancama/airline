package com.flexdevit.airline.repository.doobie

import cats.data.OptionT
import cats.effect.MonadCancel
import cats.implicits.toFunctorOps
import com.flexdevit.airline.domain.airline.AirlineRepositoryAlgebra
import com.flexdevit.airline.domain.airline.airline.Airline
import doobie.{Query0, Transactor, Update0}
import doobie.implicits.toSqlInterpolator
import doobie.implicits._
private object AirlineSQL {
  def insert(airline: Airline): Update0 = sql"""
      INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active)
      VALUES (${airline.id}, ${airline.name}, ${airline.alias}, ${airline.iataId}, ${airline.icaoId}, ${airline.callSign}, ${airline.country}, ${airline.active})
    """.update

  def fetchId: Query0[Long] = sql"""
      SELECT nextval('airline_seq')
       """.query[Long]

  def findById(id: Long): Query0[Airline] = sql"""
      SELECT id, name, alias, iata_id, icao_id, call_sign, country, active from airline
      where id = $id
       """.query[Airline]

  def findAllAirlines: Query0[Airline] = sql"""
      SELECT id, name, alias, iata_id, icao_id, call_sign, country, active from airline
       """.query[Airline]

  def deleteArlineById(airlineId: Long): Update0 = sql"""
      DELETE FROM airline
      where id $airlineId
       """.update
}
class AirlineRepositoryPostgresInterpreter[F[_]: MonadCancel[*[_], Throwable]](val xa: Transactor[F]) extends AirlineRepositoryAlgebra[F] {
  import AirlineSQL._
  override def create(airline: Airline): F[Airline] = {
    val connectionIO = for {
      id <- fetchId.unique
      insertedAirline = airline.copy(Some(id))
      _ <- insert(insertedAirline).run
    } yield insertedAirline
    connectionIO.transact(xa)
  }

  override def get(id: Long): F[Option[Airline]] =
    findById(id).option.transact(xa)

  override def findAll(): F[List[Airline]] = findAllAirlines.to[List].transact(xa)

  override def delete(id: Long): F[Option[Airline]] = {
    OptionT(get(id)).semiflatMap(airline => deleteArlineById(id).run.transact(xa).as(airline)).value
  }
}
