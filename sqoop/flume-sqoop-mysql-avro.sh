#Agent Name = step1

# Name the source, channel and sink
step1.sources = avro-source  
step1.channels = jdbc-channel
step1.sinks = file-sink

# Source configuration
step1.sources.avro-source.type = avro
step1.sources.avro-source.port = 11112
step1.sources.avro-source.bind = localhost


# Describe the sink
step1.sinks.file-sink.type = hdfs
step1.sinks.file-sink.hdfs.path = /user/cloudera/problem7/sink
step1.sinks.file-sink.hdfs.fileType = DataStream
step1.sinks.file-sink.hdfs.fileSuffix = .avro
step1.sinks.file-sink.serializer = avro_event
step1.sinks.file-sink.serializer.compressionCodec=snappy

# Describe the type of channel --  Use memory channel if jdbc channel does not work
step1.channels.jdbc-channel.type = jdbc

# Bind the source and sink to the channel
step1.sources.avro-source.channels = jdbc-channel
step1.sinks.file-sink.channel = jdbc-channel
