import sbt._
import sbt.Keys._
import sbtrelease.ReleasePlugin
import com.typesafe.sbt.pgp.PgpKeys
import bintray.BintrayPlugin.autoImport._

/** Adds common settings automatically to all subprojects */
object GlobalPlugin extends AutoPlugin {

  val org = "com.sksamuel.avro4s"

  val AvroVersion = "1.8.1"
  val Log4jVersion = "1.2.17"
  val ScalatestVersion = "3.0.0"
  val ScalaVersion = "2.12.1"
  val Slf4jVersion = "1.7.12"

  override def requires = ReleasePlugin
  override def trigger = allRequirements
  override def projectSettings = publishingSettings ++ Seq(
    organization := org,
    bintrayOrganization := Some("ovotech"),
    licenses in ThisBuild += ("MIT", url("http://opensource.org/licenses/MIT")),
    scalaVersion := ScalaVersion,
    crossScalaVersions := Seq("2.11.8", "2.12.1"),
    resolvers += Resolver.mavenLocal,
    parallelExecution in Test := false,
    scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Ywarn-unused-import",
      "-Xfatal-warnings", "-feature", "-language:existentials"
    ),
    javacOptions := Seq("-source", "1.7", "-target", "1.7"),
    libraryDependencies ++= Seq(
      "org.scala-lang"    % "scala-reflect"     % scalaVersion.value,
      "org.apache.avro"   % "avro"              % AvroVersion,
      "org.slf4j"         % "slf4j-api"         % Slf4jVersion,
      "log4j"             % "log4j"             % Log4jVersion          % "test",
      "org.slf4j"         % "log4j-over-slf4j"  % Slf4jVersion          % "test",
      "org.scalatest"     %% "scalatest"        % ScalatestVersion      % "test"
    )
  )

  val publishingSettings = Seq(
    publishMavenStyle := true,
    publishArtifact in Test := false,
    ReleasePlugin.autoImport.releaseCrossBuild := true,
    pomExtra := {
      <url>https://github.com/sksamuel/avro4s</url>
        <licenses>
          <license>
            <name>MIT</name>
            <url>https://opensource.org/licenses/MIT</url>
            <distribution>repo</distribution>
          </license>
        </licenses>
        <scm>
          <url>git@github.com:sksamuel/avro4s.git</url>
          <connection>scm:git@github.com:sksamuel/avro4s.git</connection>
        </scm>
        <developers>
          <developer>
            <id>sksamuel</id>
            <name>sksamuel</name>
            <url>http://github.com/sksamuel</url>
          </developer>
        </developers>
    }
  )
}
