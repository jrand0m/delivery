import sbt._
import play.Project._
import com.typesafe.config._

object ApplicationBuild extends Build {

  val appName = "vdoma"
  val appVersion = scala.util.Properties.envOrElse("appVersion", "999.0-SNAPSHOT" )

  val appDependencies = Seq(
    // Add your project dependencies here,
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "com.google.inject" % "guice" % "3.0",
    "org.joda" % "joda-money" % "0.7"
      withSources(),
    "joda-time" % "joda-time" % "2.1",
    "org.apache.commons" % "commons-lang3" % "3.1",
    javaCore,
    javaJdbc,
    javaEbean

  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )

}
