package com.github.melifaro.routes

import com.github.melifaro.auth.{AuthHttp4s, UserModel}

object Http4sRoutes {

  import cats.effect._
  import org.http4s._
  import org.http4s.circe.CirceEntityCodec._
  import org.http4s.dsl.Http4sDsl

  private def userRoutes: AuthedRoutes[UserModel, IO] = {
    object dsl extends Http4sDsl[IO] {}
    import dsl._

    AuthedRoutes.of {
      case GET -> Root / "user" / "greet" as user =>
        println(s"greet user $user")
        Ok(s"Hello, $user")
    }
  }

  def routes: HttpRoutes[IO] = AuthHttp4s.authMiddleware().apply(userRoutes)

}
