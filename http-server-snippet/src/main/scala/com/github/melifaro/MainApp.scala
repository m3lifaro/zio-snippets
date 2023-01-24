package com.github.melifaro

import zhttp.service.Server
import zio._
import zhttp.http._
import zhttp.http.middleware.HttpMiddleware

object MainApp extends ZIOAppDefault {
  def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server.start(
      port = 8080,
      http = Http.collect[Request] {
        case Method.GET -> !! / "greet" / name =>
          Response.text(s"Hello $name!")
      }  @@ JwtTokenMiddleware()
    )

}

object JwtTokenMiddleware {
  import pdi.jwt._
  import pdi.jwt.algorithms.JwtHmacAlgorithm
  import io.circe.jawn.decode
  import io.circe._, io.circe.generic.semiauto._
  import scala.util.{Failure, Success}

  implicit val userTokenDecoder: Decoder[UserToken] = deriveDecoder[UserToken]
  implicit val userTokenEncoder: Encoder[UserToken] = deriveEncoder[UserToken]
  val ALGORITHM: JwtHmacAlgorithm = JwtAlgorithm.HS256
  private val key = "secret-key"
  case class UserToken(uid: String, uname: String, roles: Seq[String])

  def readToken(str: String): ZIO[Any, Throwable, Boolean] = {
    val token = for {
      claim <- JwtCirce.decode(str, key, Seq(ALGORITHM), JwtOptions.DEFAULT)
      info <- decode[UserToken](claim.content) match {
        case Right(cl) => Success(cl)
        case Left(er) => Failure(new Exception(er.getMessage))
      }
    } yield info

    ZIO.fromTry(token.map(_ => true)).provide(ZLayer.empty)
  }

  def apply(): HttpMiddleware[Any, Nothing] = Middleware.bearerAuthZIO(readToken(_))
}