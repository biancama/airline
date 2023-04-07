package suite

import cats.effect.IO
import cats.implicits.catsSyntaxSemigroup
import io.circe.Encoder
import io.circe.syntax.EncoderOps
import org.http4s.circe.toMessageSyntax
import org.http4s.{HttpRoutes, Request, Status}
import weaver.{Expectations, SimpleIOSuite}
import weaver.scalacheck.Checkers

trait HttpSuite extends SimpleIOSuite with Checkers {
  def expectHttpBodyAndStatus[A: Encoder](routes: HttpRoutes[IO], req: Request[IO])(
    expectedBody: A,
    expectedStatus: Status
  ): IO[Expectations] =
    routes.run(req).value.flatMap {
      case Some(resp) =>
        resp.asJson.map(json => expect.same(resp.status, expectedStatus) |+|
        expect.same(json.dropNullValues, expectedBody.asJson.dropNullValues))
      case None => IO.pure(failure("route not found"))
    }

  def expectHttpStatus(routes: HttpRoutes[IO], req: Request[IO])(expectedStatus: Status): IO[Expectations] =
    routes.run(req).value.map {
      case Some(resp) => expect.same(resp.status, expectedStatus)
      case None => failure("route not found")
    }
}
