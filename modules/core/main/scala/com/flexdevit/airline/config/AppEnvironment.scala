package com.flexdevit.airline.config

import enumeratum.EnumEntry.Lowercase
import enumeratum.{CirisEnum, Enum, EnumEntry}
sealed abstract class AppEnvironment extends EnumEntry with Lowercase
object AppEnvironment extends Enum[AppEnvironment]  with CirisEnum[AppEnvironment] {

  case object Test   extends AppEnvironment
  case object Production extends AppEnvironment

  val values = findValues
}