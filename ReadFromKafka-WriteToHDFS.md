Start Kafka:
==============
Kafka Home: /opt/apache_kafka/kafka_2.11-0.9.0.1
Flume Home: /usr/lib/flume-ng


Starting & Setting Kafka:
[cloudera@quickstart kafka_2.11-0.9.0.1]$ sudo ./bin/zookeeper-server-start.sh config/zookeeper.properties 

[cloudera@quickstart kafka_2.11-0.9.0.1]$ sudo ./bin/kafka-server-start.sh config/server.properties 

[cloudera@quickstart kafka_2.11-0.9.0.1]$sudo ./bin/kafka-topics.sh --create --topic HelloKafkaTopic --partition 1 --replication-factor 1 --zookeeper localhost:2181 		

Flume Setup:

Conf File Name: $FLUME_HOME/conf/flume-kafka-source-hdfs-sink.conf
[cloudera@quickstart flume-ng]$ sudo ./bin/flume-ng agent --conf conf -conf-file conf/flume-kafka-source-hdfs-sink.conf --name flume1

Start Kafka Producer: 

[cloudera@quickstart kafka_2.11-0.9.0.1]$ sudo ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic HelloKafkaTopic

How to check: 
=============

command: hadoop fs -cat /tmp/kafka/HelloKafkaTopic/17-03-17/
You should have files inside this folder containing the kafka messages

