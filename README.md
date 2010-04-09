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

**Second**, point it towards your Avro schemas and/or protocols:
    
    override def avroProtocols = Some("src" / "main" / "avro" / "happy-puppy-protocol.avpr")
    override def avroSchemas = Some("src" / "main" / "avro" / "shower-magic-schema.avpr")
    
**Third**, and only if you're feeling picky, let it know where you want your
new classes put:
    
    override def avroOutputPath = "src" / "main" / "generated"

(It defaults to `src/main/java`, which will probably work just fine for you.)


**Finally**, compile your project. avro-sbt will generate fresh source files for
your schemas and protocols before it compiles things. It should *just work*.


License
-------

Copyright (c) 2010 Coda Hale
Published under The MIT License, see LICENSE