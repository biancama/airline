package com.flexdevit.airline.ext.ciris

import _root_.ciris.ConfigDecoder
import com.flexdevit.airline.ext.derevo.Derive

object configDecoder extends Derive[Decoder.Id]

object Decoder {
  type Id[A] = ConfigDecoder[String, A]
}
