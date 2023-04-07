package com.flexdevit.airline.resources

import cats.effect.{Async, Resource}
import com.flexdevit.airline.config.types.HttpServerConfig
import org.http4s.HttpApp
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server

trait MkHttpServer[F[_]] {
  def newEmber(cfg: HttpServerConfig, httpApp: HttpApp[F]): Resource[F, Server]
}

object MkHttpServer {
  def apply[F[_]: MkHttpServer]: MkHttpServer[F] = implicitly

  implicit def forAsyncLogger[F[_]:Async]: MkHttpServer[F] = new MkHttpServer[F] {
    override def newEmber(cfg: HttpServerConfig, httpApp: HttpApp[F]): Resource[F, Server] = EmberServerBuilder.default[F]
      .withHost(cfg.host)
      .withPort(cfg.port)
      .withHttpApp(httpApp)
      .build
  }
}