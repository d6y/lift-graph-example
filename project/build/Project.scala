import sbt._
import de.element34.sbteclipsify._


class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) with Eclipsify {
  val mavenLocal = "Local Maven Repository" at  "file://"+Path.userHome+"/.m2/repository"
  val scalatoolsSnapshot = "Scala Tools Snapshot" at "http://scala-tools.org/repo-snapshots/"
  val scalatoolsRelease = "Scala Tools Snapshot" at "http://scala-tools.org/repo-releases/"

  val liftVersion = "2.4"

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default" withSources(),
    "net.liftweb" %% "lift-testkit" % liftVersion % "compile->default",
    //"net.liftweb" %% "lift-widgets" % liftVersion % "compile->default",
    "net.liftmodules" %% "widgets" % (liftVersion+"-1.0-SNAPSHOT") % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
    "junit" % "junit" % "4.5" % "test->default",
    "org.scala-tools.testing" % "specs" % "1.6.1" % "test->default"
  ) ++ super.libraryDependencies
}
