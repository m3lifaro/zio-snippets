val GLOBAL_VERSION = "0.0.1"

scalaVersion := "2.13.10"
val authentikat ="com.jason-goodwin" %% "authentikat-jwt" % "0.4.5"

  val core = "io.circe" %% "circe-core" % "0.14.3"
  val generic = "io.circe" %% "circe-generic" % "0.14.3"
  val parser = "io.circe" %% "circe-parser" % "0.14.3"
  val jawn = "io.circe" %% "circe-jawn" % "0.14.3"

  val all = Seq(core, generic, parser, jawn)

lazy val `zio-http-server` = (project in file("http-server-snippet"))
  .settings(Seq(name := "http-server-snippet", version := GLOBAL_VERSION))
  .settings(libraryDependencies ++= Seq(
    "dev.zio"       %% "zio"            % "2.0.6",
    "dev.zio"       %% "zio-json"       % "0.4.2",
    "io.d11"        %% "zhttp"          % "2.0.0-RC10",
    authentikat,
      "com.github.jwt-scala" %% "jwt-circe" % "9.1.2"
    ) ++ all)
