class AvroSbt(info: sbt.ProjectInfo) extends sbt.PluginProject(info) {
  val avro = "org.apache.hadoop" % "avro" % "1.3.2" withSources()
  val jopt = "net.sf.jopt-simple" % "jopt-simple" % "3.2" withSources()
  
  /**
   * Publish to a local temp repo, then rsync the files over to repo.codahale.com.
   */
  override def managedStyle = sbt.ManagedStyle.Maven
  val publishTo = sbt.Resolver.file("Local Cache", ("." / "target" / "repo").asFile)
  def publishToLocalRepoAction = super.publishAction
  override def publishAction = task {
    log.info("Uploading to repo.codahale.com")
    sbt.Process("rsync", "-avz" :: "target/repo/" :: "codahale.com:/home/codahale/repo.codahale.com" :: Nil) ! log
    None
  } describedAs("Publish binary and source JARs to repo.codahale.com") dependsOn(test, publishToLocalRepoAction)
}