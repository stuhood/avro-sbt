class AvroSbt(info: sbt.ProjectInfo) extends sbt.PluginProject(info) {
  val avro = "org.apache.hadoop" % "avro" % "1.3.2" withSources()
  val jopt = "net.sf.jopt-simple" % "jopt-simple" % "3.2" withSources()
}