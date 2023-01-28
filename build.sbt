val GLOBAL_VERSION = "0.0.1"




val authentikat = "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5"

val core = "io.circe" %% "circe-core" % "0.11.1"
val generic = "io.circe" %% "circe-generic" % "0.11.1"
val parser = "io.circe" %% "circe-parser" % "0.11.1"
val jawn = "io.circe" %% "circe-jawn" % "0.11.1"

lazy val http4sVersion = "0.20.8"

val all = Seq(core, generic, parser, jawn)

lazy val `zio-http-server` = (project in file("http-server-snippet"))
  .settings(scalacOptions ++= Seq("-Ypartial-unification"))
  .settings(scalaVersion := "2.12.6")
  .settings(Seq(name := "http-server-snippet", version := GLOBAL_VERSION))
  .settings(mainClass := Some("com.github.melifaro.MainApp"))

  .settings(
      dependencyOverrides := all.toSet
  )
  .settings(libraryDependencies ++= Seq(
    "dev.zio" %% "zio" % "2.0.6",
    "dev.zio" %% "zio-json" % "0.4.2",
    "io.d11" %% "zhttp" % "2.0.0-RC10",
    authentikat,
    "com.github.jwt-scala" %% "jwt-circe" % "9.1.2",
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-server" % http4sVersion,
    "org.http4s" %% "http4s-core" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion
  ) ++ all)
addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.0")
