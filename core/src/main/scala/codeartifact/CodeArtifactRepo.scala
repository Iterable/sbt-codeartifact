package codeartifact

import sbt._

case class CodeArtifactRepo(
  name: String,
  domain: String,
  host: String,
  owner: String,
  region: String,
  url: String
) {
  def realm: String = s"$domain/$name"
  def resolver: MavenRepository = realm.at(url)
}

object CodeArtifactRepo {
  private val CodeArtifactUrl = "https://((.*)-(.*).d.codeartifact.(.*).amazonaws.com)/maven/(.*)".r

  def fromUrl(url: String): CodeArtifactRepo = {
    url match {
      case CodeArtifactUrl(host, domain, owner, region, repo) =>
        CodeArtifactRepo(name = repo, domain = domain, host = host, owner = owner, region = region, url = url)
      case _ =>
        sys.error(s"Invalid codeArtifactUrl: $url")
    }
  }
}
