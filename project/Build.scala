import sbt._
import play.Project._
import com.typesafe.config._
import de.johoop.jacoco4sbt.JacocoPlugin._

object ApplicationBuild extends Build {

  val appName = "vdoma"
  val appVersion = scala.util.Properties.envOrElse("appVersion", "999.0-SNAPSHOT" )
  lazy val s = Defaults.defaultSettings ++ Seq(jacoco.settings:_*)
  val appDependencies = Seq(
    // Add your project dependencies here,
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "com.google.inject" % "guice" % "3.0",
    "org.joda" % "joda-money" % "0.7"
      withSources(),
    "joda-time" % "joda-time" % "2.1",
    "org.apache.commons" % "commons-lang3" % "3.1",
    "org.mockito" % "mockito-all" % "1.9.5",
    javaCore,
    javaJdbc,
    javaEbean

  )

  val main = play.Project(appName, appVersion, appDependencies, settings = s).settings(
        //parallelExecution in jacoco.Config := false
  )

}
