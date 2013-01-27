import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "vdo.ma"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "com.typesafe" % "play-plugins-guice" % "2.0.3",
      "org.joda" % "joda-money" % "0.7",
      "joda-time" % "joda-time" % "2.1"

    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
