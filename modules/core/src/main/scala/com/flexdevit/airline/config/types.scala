package com.flexdevit.airline.config

import com.comcast.ip4s.{ Host, Port }

object types {

  case class AppConfig(httpServerConfig: HttpServerConfig)
  case class HttpServerConfig(host: Host, port: Port)
}
