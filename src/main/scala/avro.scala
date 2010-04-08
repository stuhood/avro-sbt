package avro

import sbt._
import org.apache.avro.specific.SpecificCompiler

trait AvroCompiler extends DefaultProject {
  override def compileOrder = CompileOrder.JavaThenScala
  def avroSchemas: Option[PathFinder] = None
  def avroProtocols: Option[PathFinder] = None
  def avroOutputPath = "src" / "main" / "java"
  
  def generateAvroAction = task {
    avroSchemas.map { schemas =>
      for (schema <- schemas.get) {
        log.info("Generating classes from %s".format(schema))
        SpecificCompiler.compileSchema(schema.asFile, avroOutputPath.asFile)
      }
    }
    
    avroProtocols.map { protocols =>
      for (protocol <- protocols.get) {
        log.info("Generating classes from %s".format(protocol))
        SpecificCompiler.compileProtocol(protocol.asFile, avroOutputPath.asFile)
      }
    }
    None
  } describedAs("Generates Java classes from the specified Avro schema and protocol files.")
  lazy val generateAvro = generateAvroAction
  
  override def compileAction = super.compileAction dependsOn(generateAvro)
}