package com.flexdevit.airline

import org.http4s.{ Request, Response }

package object routes {
  type ReqToRes[F[_]] = PartialFunction[Request[F], F[Response[F]]]
}
