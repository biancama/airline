package com.flexdevit.airline.resources


import cats.effect.{Async, Resource}
import cats.implicits._
import com.flexdevit.airline.config.types.{AppConfig, DatabaseConfig}
import doobie._
import doobie.hikari.HikariTransactor
import doobie.implicits._
import org.typelevel.log4cats.Logger

import scala.concurrent.ExecutionContext
sealed abstract class AppResources[F[_]] (
  val xa: Transactor[F]
                                         )
object AppResources {

  def make[F[_]: Async: Logger](appConfig : AppConfig, executionContext: ExecutionContext): Resource[F, AppResources[F]] = {

    def checkPostgresConnection(xa: Transactor[F]): F[Unit] =
      sql"select version()".query[String].unique.transact(xa)
        .flatMap(v => Logger[F].info(s"Connected to Postgres $v"))

    def dbTransactor(dbc: DatabaseConfig, connEc: ExecutionContext,
                       ): Resource[F, Transactor[F]] = {
      HikariTransactor
        .newHikariTransactor[F](dbc.driver, dbc.url, dbc.user, dbc.password, connEc)
        .evalTap(checkPostgresConnection)
    }

    dbTransactor(appConfig.postgreSQLConfig, executionContext).map(xa => new AppResources[F](xa) {})
  }
}