package com.github.melifaro.auth

import zhttp.http.Middleware
import zhttp.http.middleware.HttpMiddleware
import zio.ZIO

object JwtTokenMiddlewareZIO {

  def readTokenZIO(tokenHeader: String): ZIO[Any, Throwable, Boolean] = {
    val token = JwtTokenHelper.readToken(tokenHeader)

    ZIO.fromTry(token.map(_ => true))
      .orDie
      .unrefine {
        case e =>
          println(e.getMessage)
          e
      }
  }

  def apply(): HttpMiddleware[Any, Throwable] = Middleware.bearerAuthZIO(readTokenZIO)

}
