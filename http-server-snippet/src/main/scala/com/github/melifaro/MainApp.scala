package com.github.melifaro

import com.github.melifaro.auth.{Http4sServer, JwtTokenMiddlewareZIO}
import zhttp.service.Server
import zio._
import zhttp.http._

object MainApp extends ZIOAppDefault {

  val nativeZioHttpServerEffect: ZIO[Any, Throwable, Nothing] = {
    println("ZIO ONLINE")
    Server.start(
      port = 8080,
      http = Http.collect[Request] {
        case req@Method.GET -> !! / "greet" / name =>
          Response.text(s"Hello $name!")
      } @@ JwtTokenMiddlewareZIO()
    )
  }
  def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = {
    nativeZioHttpServerEffect
  }

}



import cats.effect._
import cats.effect.ExitCode

object MainAppHttp4s extends IOApp {

  override def run(args: List[String]): cats.effect.IO[ExitCode] =
    Http4sServer.instance()
      .serve
      .compile
      .drain
      .map(_ => ExitCode.Success)

}