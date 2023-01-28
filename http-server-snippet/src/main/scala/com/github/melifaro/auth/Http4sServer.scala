package com.github.melifaro.auth

import cats.effect._
import com.github.melifaro.routes.Http4sRoutes
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

object Http4sServer {

  def instance()(implicit ce: ConcurrentEffect[IO], t: Timer[IO]): BlazeServerBuilder[IO] =
    BlazeServerBuilder[IO]
      .bindHttp(8080)
      .withHttpApp(Http4sRoutes.routes.orNotFound)
}
