package com.github.melifaro.auth

import cats.data._
import cats.effect._

import org.http4s._
import org.http4s.server.AuthMiddleware
import org.http4s.util.CaseInsensitiveString

object AuthHttp4s {

  implicit class BearerTokenOps(value: Option[Header]) {
    val BearerSchemeName = "Bearer"

    def bearerToken: Option[String] =
      value
        .map(_.value)
        .flatMap(v => {
          val indexOfBearer = v.indexOf(BearerSchemeName)
          if (indexOfBearer != 0 || v.length == BearerSchemeName.length) {
            None
          } else
            Some(v.substring(BearerSchemeName.length + 1))
        })
  }

  def authUser(): Kleisli[IO, Request[IO], Either[Throwable, UserModel]] = Kleisli { request: Request[IO] =>
    val maybeToken: Option[String] = request.headers.get(CaseInsensitiveString("Authorization")).bearerToken
    maybeToken match {
      case Some(token) =>
        IO.fromTry(JwtTokenHelper.readToken(token)).map(Right(_))
      case None => IO.pure(Left(new RuntimeException("Token not found")))
    }
  }

  def onAuthFailure: AuthedRoutes[Throwable, IO] = Kleisli { req: AuthedRequest[IO, Throwable] =>
    req.req match {
      case _ =>
        OptionT.pure[IO](
          Response[IO](
            status = Status.Unauthorized
          )
        )
    }
  }

  def authMiddleware(): AuthMiddleware[IO, UserModel] = AuthMiddleware(authUser(), onAuthFailure)

}