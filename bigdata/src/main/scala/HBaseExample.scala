import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.client.{HBaseAdmin, HTable, Put, Result}
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.hbase.CellUtil
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.JavaConverters._
import org.apache.hadoop.hbase.KeyValue.Type


object HBaseExample {
        def main(args: Array[String]) {
        val sparkConf = new SparkConf().setAppName("HBaseRead").setMaster("local[2]")
        val sc = new SparkContext(sparkConf)
        val conf = HBaseConfiguration.create()
        val tableName = "emp"

        System.setProperty("user.name", "hdfs")
        System.setProperty("HADOOP_USER_NAME", "hdfs")
        conf.set("hbase.master", "127.0.0.1:60000")
        conf.setInt("timeout", 120000)
        conf.set("hbase.zookeeper.quorum", "localhost")
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("zookeeper.znode.parent", "/hbase")
        conf.set(TableInputFormat.INPUT_TABLE, tableName)
        conf.addResource(new Path("/etc/hbase/conf/hbase-site.xml"))
/*        val conf = HBaseConfiguration.create();
        conf.addResource(new Path("/etc/hbase/conf/core-site.xml"));
        conf.addResource(new Path("/etc/hbase/conf/hbase-site.xml"));
        //val hbaseContext = new HBaseContext(sc, conf);*/

/*        val admin = new HBaseAdmin(conf)
        if (!admin.isTableAvailable(tableName)) {
          val tableDesc = new HTableDescriptor(tableName)
          admin.createTable(tableDesc)
        }*/
        val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat], classOf[ImmutableBytesWritable], classOf[Result])
        println("Number of Records found : " + hBaseRDD.count())
        val keyValue = hBaseRDD.map(x => x._2).map(_.list)

        val outPut = keyValue.flatMap(x =>  x.asScala.map(cell =>
          "columnFamily=%s,qualifier=%s,timestamp=%s,type=%s,value=%s".format(
            Bytes.toStringBinary(CellUtil.cloneFamily(cell)),
            Bytes.toStringBinary(CellUtil.cloneQualifier(cell)),
            cell.getTimestamp.toString,
            Type.codeToType(cell.getTypeByte),
            Bytes.toStringBinary(CellUtil.cloneValue(cell))
          )
        )
        )
        outPut.foreach(println)

        val myTable = new HTable(conf, tableName);
        for (i <- 0 to 1) {
          //var p = new Put();
          var p = new Put(new String("row" + i).getBytes());
          p.add("per_data".getBytes(), "per_data".getBytes(), new String("Mumbai").getBytes());
          myTable.put(p);
        }
        myTable.flushCommits();
        sc.stop()

      }}
