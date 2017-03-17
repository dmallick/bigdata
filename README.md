# bigdata
Start Kafka:
==============
Kafka Home: /opt/apache_kafka/kafka_2.11-0.9.0.1
[cloudera@quickstart kafka_2.11-0.9.0.1]$ sudo ./bin/zookeeper-server-start.sh config/zookeeper.properties
[cloudera@quickstart kafka_2.11-0.9.0.1]$ sudo ./bin/kafka-server-start.sh config/server.properties
[cloudera@quickstart kafka_2.11-0.9.0.1]$ sudo ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic HelloKafkaTopic		
