

import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


object KafkaToHbase {
def main(args: Array[String]): Unit =
  {
    if (args.length < 2)
    {
      System.err.println("Usage: <broker-list> <zk-list> <topic>")
      System.exit(1)
    }

    val Array(broker, zk, topic) = args

    val sparkConf = new SparkConf().setAppName("KafkaHBaseWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    val kafkaConf = Map("metadata.broker.list" -> broker,
      "zookeeper.connect" -> zk,
      "group.id" -> "kafka-spark-streaming-example",
      "zookeeper.connection.timeout.ms" -> "1000")

    val lines = KafkaUtils.createStream[Array[Byte], String, DefaultDecoder, StringDecoder](
      ssc, kafkaConf, Map(topic -> 1), StorageLevel.MEMORY_ONLY_SER).map(_._2)
    val words = lines.flatMap(_.split(" "))

    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)

    wordCounts.foreachRDD ( rdd => {
      println("--------------")
      val conf = HBaseConfiguration.create()
      conf.set(TableOutputFormat.OUTPUT_TABLE, "stream_count")
      conf.set("hbase.zookeeper.quorum", "localhost:2181")
      conf.set("hbase.master", "localhost:60000");
      conf.set("hbase.rootdir", "file:///tmp/hbase")

      val jobConf = new Configuration(conf)
      jobConf.set("mapreduce.job.output.key.class", classOf[Text].getName)
      jobConf.set("mapreduce.job.output.value.class", classOf[LongWritable].getName)
      jobConf.set("mapreduce.outputformat.class", classOf[TableOutputFormat[Text]].getName)
      //rdd.saveAsNewAPIHadoopDataset(jobConf)
      rdd.map(convert).saveAsNewAPIHadoopDataset(jobConf)

    })

    ssc.start()
    ssc.awaitTermination()
  }

  def putRequest(t: (String, Long)) = {
    val p = new Put(Bytes.toBytes(t._1))
    p.add(Bytes.toBytes("word"), Bytes.toBytes("count"), Bytes.toBytes(t._2))
  }

  def convert(t: (String, Long)) = {
    val p = new Put(Bytes.toBytes(t._1))
    p.add(Bytes.toBytes("word"), Bytes.toBytes("count"), Bytes.toBytes(t._2))
    (t._1, p)
  }

/*  def convert(t: (String, Long)) = {
    val p = new Put(Bytes.toBytes(t._1))
    p.add(Bytes.toBytes("word"), Bytes.toBytes("count"), Bytes.toBytes(t._2))
    (t._1, p)
  }*/
}
