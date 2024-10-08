package codeartifact

import sbt._

trait CodeArtifactKeys {

  ///////////
  // Tasks //
  ////////// .

  val codeArtifactPublish: TaskKey[Unit] =
    taskKey[Unit]("Publish to AWS CodeArtifact.")

  //////////////
  // Settings //
  //////////////

  val codeArtifactUrl: SettingKey[String] =
    settingKey[String]("CodeArtifact connection url.")

  val codeArtifactResolvers: SettingKey[List[String]] =
    settingKey[List[String]]("Additional CodeArtifact repos from which to consume packages.")

  val codeArtifactGetTokenInstructions =
    settingKey[Option[String]]("Instructions on how to get a token for CodeArtifact")
}

trait InternalCodeArtifactKeys {
  val codeArtifactReadTimeout = taskKey[Int]("API request read timeout. Defaults to 1 minute.")
  val codeArtifactConnectTimeout = taskKey[Int]("API request read timeout. Defaults to 30 seconds.")
  val codeArtifactRepo = taskKey[CodeArtifactRepo]("CodeArtifact repository.")
  val codeArtifactPackage = taskKey[CodeArtifactPackage]("CodeArtifact package.")

  val codeArtifactToken = taskKey[Option[String]](
    "CodeArtifact authentication. Provided by environment variable CODEARTIFACT_AUTH_TOKEN or fetched dynamically from available AWS credentials."
  )
}

object CodeArtifactKeys extends CodeArtifactKeys

object InternalCodeArtifactKeys extends InternalCodeArtifactKeys
