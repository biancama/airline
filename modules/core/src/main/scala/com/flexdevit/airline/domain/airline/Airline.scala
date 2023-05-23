package com.flexdevit.airline.domain.airline

import derevo.cats.{eqv, show}
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import eu.timepit.refined.types.all.NonEmptyString
import io.estatico.newtype.macros.newtype
import eu.timepit.refined.auto._
import eu.timepit.refined.cats._
import io.circe.{Codec, Decoder, Encoder}
import io.circe.generic.semiauto.{deriveCodec, deriveDecoder, deriveEncoder}
import io.circe.refined._

object airline {
  @derive(decoder, encoder, eqv, show)
  case class Airline(
      id: Option[Long] = None,
      name: String,
      alias: String,
      iataId: String,
      icaoId: String,
      callSign: String,
      country: String,
      active: Boolean
  ) {}

  object Airline {
    implicit val jsonEncoder: Encoder.AsObject[Airline] = deriveEncoder[Airline]
    implicit val jsonDecoder: Decoder[Airline] = deriveDecoder[Airline]

  }

  // ---------------- create airline -------------------------------------------
  @derive(decoder, encoder, show)
  @newtype
  case class AirlineNameParam(value: NonEmptyString)

  @derive(decoder, encoder, show)
  @newtype
  case class AirlineAliasParam(value: NonEmptyString)

  @derive(decoder, encoder, show)
  @newtype
  case class AirlineIataIdParam(value: NonEmptyString)

  @derive(decoder, encoder, show)
  @newtype
  case class AirlineIcaoIdParam(value: NonEmptyString)

  @derive(decoder, encoder, show)
  @newtype
  case class AirlineCallSignParam(value: NonEmptyString)

  @derive(decoder, encoder, show)
  @newtype
  case class AirlineCountryParam(value: NonEmptyString)
  @derive(decoder, encoder, show)
  case class CreateAirlineParam(
      name: AirlineNameParam,
      alias: AirlineAliasParam,
      iataId: AirlineIataIdParam,
      icaoId: AirlineIcaoIdParam,
      callSign: AirlineCallSignParam,
      country: AirlineCountryParam
  ) {
    def toDomain: Airline =
      Airline(
        None,
        name.value.value,
        alias.value.value,
        iataId.value.value,
        icaoId.value.value,
        callSign.value.value,
        country.value.value,
        true
      )
  }

}
