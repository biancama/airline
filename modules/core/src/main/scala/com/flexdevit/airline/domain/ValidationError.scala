package com.flexdevit.airline.domain

sealed trait ValidationError
case object AirlineNotFoundError extends ValidationError
