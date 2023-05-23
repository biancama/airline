package suite

import cats.effect.IO
import cats.implicits.catsSyntaxSemigroup
import io.circe.Encoder
import io.circe.syntax.EncoderOps
import org.http4s.Header.Raw
import org.http4s.circe.toMessageSyntax
import org.http4s.syntax.header
import org.http4s.{Header, Headers, HttpRoutes, Request, Status}
import weaver.{Expectations, SimpleIOSuite}
import weaver.scalacheck.Checkers

trait HttpSuite extends SimpleIOSuite with Checkers {
  def expectHttpBodyAndStatus[A: Encoder](routes: HttpRoutes[IO], req: Request[IO])(
    expectedBody: A,
    expectedStatus: Status
  ): IO[Expectations] =
    routes.run(req).value.flatMap {
      case Some(resp) =>
        resp.asJson.map(json => expect.same(expectedStatus, resp.status) |+|
        expect.same(expectedBody.asJson.dropNullValues, json.dropNullValues))
      case None => IO.pure(failure("route not found"))
    }

  def expectHttpStatus(routes: HttpRoutes[IO], req: Request[IO])(expectedStatus: Status): IO[Expectations] =
    routes.run(req).value.map {
      case Some(resp) => expect.same(expectedStatus, resp.status)
      case None => failure("route not found")
    }

  def expectHttpStatusAndHeadersContains[A, T <: Header.Type](routes: HttpRoutes[IO], req: Request[IO])(expectedStatus: Status, header: Raw): IO[Expectations] =
    routes.run(req).value.map {
      case Some(resp) => expect.same(resp.status, expectedStatus) |+|
        exists(resp.headers.headers)(i => expect.eql(header, i))
      case None => failure("route not found")
    }
}
