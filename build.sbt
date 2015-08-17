name := "Foo root project"

import com.typesafe.sbt.web.SbtWeb
import SbtWeb.autoImport.WebKeys._

lazy val root = project.in(file(".")).
  aggregate(appJS, appJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val app = crossProject.in(file(".")).
  settings(
    name := "app",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.11.6",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.4.6",
      "com.lihaoyi" %%% "upickle" % "0.2.7",
      "com.lihaoyi" %%% "autowire" % "0.2.5"
    ),
    scalacOptions ++= Seq("-feature")
  ).
  jvmConfigure(
    (jvmProject: Project) => {
      jvmProject.copy(settings = (Revolver.settings ++ jvmProject.settings))
    }
  ).
  jvmSettings(
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-can" % "1.3.2",
      "io.spray" %% "spray-routing" % "1.3.2",
      "com.typesafe.akka" %% "akka-actor" % "2.3.6",
      "org.xerial" % "sqlite-jdbc" % "3.8.10.1",
      "org.webjars" % "bootstrap" % "3.1.1-2",
      "com.typesafe.slick" %% "slick" % "3.0.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4"
    ),
    baseDirectory in Revolver.reStart := file("./"),
    // unmanagedResourceDirectories in Compile += file("./") / "bower_components",
    webTarget := (classDirectory in Compile).value / "web",
    Revolver.reStart <<= Revolver.reStart.dependsOn(assets)
  ).
  jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.8.0"
    ),
    jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "jquery.js",
    jsDependencies += "org.webjars" % "highcharts" % "4.0.4" / "highcharts.js"
  ).enablePlugins(SbtWeb)

lazy val appJVM = app.jvm.settings(
  (resources in Compile) ++= Seq(
    (fastOptJS in (appJS, Compile)).value.data,
    (packageJSDependencies in (appJS, Compile)).value
  )
)

lazy val appJS = app.js
