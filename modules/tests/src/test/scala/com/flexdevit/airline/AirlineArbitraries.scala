package com.flexdevit.airline

import com.flexdevit.airline.domain.airline.airline.Airline
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
    name <- Gen.nonEmptyListOf(Gen.asciiPrintableChar).map(_.mkString)
    alias <- Gen.nonEmptyListOf(Gen.asciiPrintableChar).map(_.mkString)
    iataId <- arbitrary[String]
    icaoId <- strGen(3)
    callSign <- strGen(3)
    country <- oneOf(countries)
    active <- arbitrary[Boolean]
  } yield Airline(id, name, alias, iataId, icaoId, callSign, country, active)

  val airlineNotCreated = airlineArb(Gen.const(Option.empty[Long]))

  val airlines: Arbitrary[List[Airline]] = Arbitrary(Gen.containerOf[List, Airline](airline(Gen.option(long))))
}
