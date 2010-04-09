package avro

import sbt._
import org.apache.avro.specific.SpecificCompiler

trait AvroCompiler extends DefaultProject {
  override def compileOrder = CompileOrder.JavaThenScala
  def avroSchemas = "src" / "main" / "avro" ** "*.avsc"
  def avroProtocols = "src" / "main" / "avro" ** "*.avpr"
  def avroOutputPath = "src" / "main" / "java"
  
  def generateAvroAction = task {
    for (schema <- avroSchemas.get) {
      log.info("Compiling schema %s".format(schema))
      SpecificCompiler.compileSchema(schema.asFile, avroOutputPath.asFile)
    }
    
    for (protocol <- avroProtocols.get) {
      log.info("Compiling protocol %s".format(protocol))
      SpecificCompiler.compileProtocol(protocol.asFile, avroOutputPath.asFile)
    }
    
    None
  } describedAs("Generates Java classes from the specified Avro schema and protocol files.")
  lazy val generateAvro = generateAvroAction
  
  override def compileAction = super.compileAction dependsOn(generateAvro)
  
  def cleanAvroAction = task {
    FileUtilities.clean(avroOutputPath :: Nil, false, log)
    None
  } describedAs("Deletes the Avro output directory.")
  
  lazy val cleanAvro = cleanAvroAction
}