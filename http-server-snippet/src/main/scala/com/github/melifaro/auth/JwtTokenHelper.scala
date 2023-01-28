package com.github.melifaro.auth

import scala.util.Try

object JwtTokenHelper {

  import io.circe._
  import io.circe.generic.semiauto._
  import io.circe.jawn.decode
  import io.circe.syntax._
  import pdi.jwt._
  import pdi.jwt.algorithms.JwtHmacAlgorithm

  import scala.util.{Failure, Success}

  implicit val userTokenDecoder: Decoder[UserModel] = deriveDecoder[UserModel]
  implicit val userTokenEncoder: Encoder[UserModel] = deriveEncoder[UserModel]
  val ALGORITHM: JwtHmacAlgorithm = JwtAlgorithm.HS256
  private val key = "secret-key"

  def readToken(str: String): Try[UserModel] = {
    for {
      claim <- JwtCirce.decode(str, key, Seq(ALGORITHM), JwtOptions.DEFAULT)
      info <- decode[UserModel](claim.content) match {
        case Right(cl) => {
          Success(cl)
        }
        case Left(er) => {
          println(er.getMessage)
          Failure(new Exception(er.getMessage))
        }
      }
    } yield info
  }
}
