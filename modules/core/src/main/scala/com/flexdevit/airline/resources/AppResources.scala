package com.flexdevit.airline.resources


import cats.effect._
import cats.implicits._
import com.flexdevit.airline.config.types.{AppConfig, PostgreSQLConfig}
import doobie._
import doobie.hikari.HikariTransactor
import doobie.implicits._
import doobie.util.ExecutionContexts
import org.typelevel.log4cats.Logger
import shapeless.Lazy.apply
sealed abstract class AppResources[F[_]] (
  val postgres: Resource[F, Transactor[F]])

object AppResources {

  def make[F[_]: Async: Logger](appConfig : AppConfig): Resource[F, AppResources[F]] = {

    def checkPostgresConnection(postgres: Resource[F, Transactor[F]]): F[Unit] =
      postgres.use { xa =>
        sql"select version()".query[String].unique.transact(xa)
          .flatMap(v => Logger[F].info(s"Connected to Postgres $v"))
      }
    def mkPostgreSqlResource(cfg: PostgreSQLConfig): Resource[F, Resource[F, HikariTransactor[F]]] = {
      val postgresResource = for {
        connEc <- ExecutionContexts.fixedThreadPool[F](cfg.poolSize.value)
        transactor <- HikariTransactor
          .newHikariTransactor[F](cfg.driver.value, cfg.url.value, cfg.user.value, cfg.password.value, connEc)
      } yield transactor
      Resource.eval[F, Resource[F, HikariTransactor[F]]](postgresResource.pure[F])
    }.evalTap(checkPostgresConnection)

    mkPostgreSqlResource(appConfig.postgreSQLConfig).map(new AppResources[F](_) {})
  }
}