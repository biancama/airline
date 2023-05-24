package com.flexdevit.airline

import com.flexdevit.airline.domain.airline.airline._
import eu.timepit.refined.scalacheck.all.nonEmptyStringArbitrary
import eu.timepit.refined.types.string.NonEmptyString
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen.{long, oneOf}
import org.scalacheck.{Arbitrary, Gen}

trait AirlineArbitraries {
  val strGen = (n: Int) => Gen.listOfN(n, Gen.alphaChar).map(_.mkString)

  val countries = Set("ITA", "NED", "FRA", "USA")
  def airlineArb(id: Gen[Option[Long]]): Arbitrary[Airline] = Arbitrary[Airline] {
    airline(id)
  }
  def airline(genId: Gen[Option[Long]]) = for {
    id <- genId
    name <- strGen(10)
    alias <- strGen(5)
    iataId <- strGen(3)
    icaoId <- strGen(3)
    callSign <- strGen(3)
    country <- oneOf(countries)
    active <- arbitrary[Boolean]
  } yield Airline(id, name, alias, iataId, icaoId, callSign, country, active)

  def airlineParam = for {
    name <- arbitrary[NonEmptyString].map(AirlineNameParam(_))
    alias <- arbitrary[NonEmptyString].map(AirlineAliasParam(_))
    iataId <- arbitrary[NonEmptyString].map(AirlineIataIdParam(_))
    icaoId <- arbitrary[NonEmptyString].map(AirlineIcaoIdParam(_))
    callSign <- arbitrary[NonEmptyString].map(AirlineCallSignParam(_))
    country <- arbitrary[NonEmptyString].map(AirlineCountryParam(_))
  } yield CreateAirlineParam(name, alias, iataId, icaoId, callSign, country)


  val airlineNotCreated: Arbitrary[CreateAirlineParam] = Arbitrary(airlineParam)
  val airlineCreated: Arbitrary[Airline] = Arbitrary(airline(long.map(Some(_))))

  val airlines: Arbitrary[List[Airline]] = Arbitrary(Gen.containerOf[List, Airline](airline(long.map(Some(_)))))
  val airlineTupled: Arbitrary[(CreateAirlineParam, Long)] = Arbitrary(for {
    airNotCreated <- airlineNotCreated.arbitrary
    id <- long
  } yield (airNotCreated, id))

}
