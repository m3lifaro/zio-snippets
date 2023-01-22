package com.github.melifaro

import zhttp.service.Server
import zio._
import zhttp.http._

object MainApp extends ZIOAppDefault {
  def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server.start(
      port = 8080,
      http = Http.collect[Request] {
        case Method.GET -> !! / "greet" / name =>
          Response.text(s"Hello $name!")
      }
    )
}