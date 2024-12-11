package codeartifact

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.codeartifact.CodeartifactClient
import software.amazon.awssdk.services.codeartifact.model.GetAuthorizationTokenRequest

import sbt._
import scala.concurrent.duration._

object CodeArtifact {

  def mkCredentials(token: String)(repo: CodeArtifactRepo): Credentials = Credentials(
    userName = "aws",
    realm = repo.realm,
    host = repo.host,
    passwd = token
  )

  private def getAuthorizationTokenRequest(domain: String, owner: String) =
    GetAuthorizationTokenRequest
      .builder()
      .domain(domain)
      .domainOwner(owner)
      .durationSeconds(15.minutes.toSeconds)
      .build()

  private def getAuthTokenFromRequest(region: Region, req: GetAuthorizationTokenRequest): String =
    CodeartifactClient
      .builder()
      .region(region)
      .build()
      .getAuthorizationToken(req)
      .authorizationToken()

  def getAuthToken(repo: CodeArtifactRepo): Option[String] =
    try {
      Some(getAuthTokenFromRequest(Region.of(repo.region), getAuthorizationTokenRequest(repo.domain, repo.owner)))
    } catch {
      case _: Throwable => None
    }

  object Defaults {
    val READ_TIMEOUT: Int = 1.minutes.toMillis.toInt
    val CONNECT_TIMEOUT: Int = 5.seconds.toMillis.toInt
  }
}
