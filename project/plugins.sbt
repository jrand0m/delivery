// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "org.jacoco" % "org.jacoco.core" % "0.6.2.201302030002" artifacts(Artifact("org.jacoco.core", "jar", "jar")),
    "org.jacoco" % "org.jacoco.report" % "0.6.2.201302030002" artifacts(Artifact("org.jacoco.report", "jar", "jar")))

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.1.5")

addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.0.0")
