val GLOBAL_VERSION = "0.0.1"

scalaVersion := "2.13.10"

lazy val `zio-http-server` = (project in file("http-server-snippet"))
  .settings(Seq(name := "http-server-snippet", version := GLOBAL_VERSION))
  .settings(libraryDependencies ++= Seq(
    "dev.zio"       %% "zio"            % "2.0.6",
    "dev.zio"       %% "zio-json"       % "0.4.2",
    "io.d11"        %% "zhttp"          % "2.0.0-RC10"
    ))
