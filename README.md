avro-sbt
========

*Get a machine to make your machine write code.*

avro-sbt is a [simple-build-tool](http://code.google.com/p/simple-build-tool/)
plugin for generating [Avro](http://hadoop.apache.org/avro/) classes from schema
and protocol definitions.


Requirements
------------

* Simple Build Tool
* A burning need to generate classes using Avro.


How To Use
----------

**First**, specify avro-sbt as a dependency in `project/plugins/Plugins.scala`:

    class Plugins(info: sbt.ProjectInfo) extends sbt.PluginDefinition(info) {
      val codaRepo = "Coda Hale's Repository" at "http://repo.codahale.com/"
      val avroSBT = "com.codahale" % "avro-sbt" % "0.1.0"
    }

**Second**, put your Avro protocol files (`*.avpr`) and schema files (`*.avsc`)
into `src/main/avro`. If you want to put them somewhere else, be sure to let
avro-sbt know:
    
    override def avroProtocols = "src" / "main" / "my-special-avro" ** "*.avro-protocol"
    override def avroSchemas = "src" / "main" / "my-special-avro" ** "*.avro-schema"
    
**Third**, and only if you're feeling picky, let it know where you want your
new classes put:
    
    override def avroOutputPath = "src" / "main" / "generated"

(It defaults to `src/main/java`, which will probably work just fine for you.)

**Finally**, compile your project. avro-sbt will generate fresh source files for
your schemas and protocols before it compiles things. It should *just work*.

Oh, and if you want Avro to clean the generated Java sources when it cleans your
compiled `.class` files, do this:
    
    override def cleanAction = super.cleanAction dependsOn(cleanAvro)

But please only do this if `avroOutputPath` doesn't have anything you would
miss. Because `clean-avro` will nuke it.


License
-------

Copyright (c) 2010 Coda Hale
Published under The MIT License, see LICENSE