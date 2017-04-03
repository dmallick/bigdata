codegen            Generate code to interact with database records
create-hive-table  Import a table definition into Hive
eval               Evaluate a SQL statement and display the results
export             Export an HDFS directory to a database table
help               List available commands
import             Import a table from a database to HDFS
import-all-tables  Import tables from a database to HDFS
list-databases     List available databases on a server
list-tables        List available tables in a database
version            Display version information
  
--compress
--compression-codec
--as-avrodatafile
--as-sequencefile
--num-mappers
--incremental, -check-column, --last-value
--incremental lastmodified --check-column last_update_date --last-value "2013-05-22 01:01:01"
--meta-connect
--split-by, --boundary-query
--mapreduce-job-name
--export-dir cities
--batch/-Dsqoop.export.records.per.statement=10
--staging-table
--update-key
--update-mode allowinsert
--columns country,city
--hive-import
--map-column-hive
--hive-overwrite
--hbase-table cities 
--column-family world

sqoop import --compress --compression-codec org.apache.hadoop.io.compress.BZip2Codec
sqoop import --connect jdbc:mysql://mysql.example.com/sqoop --username sqoop --table cities --direct
sqoop import --map-column-java c1=Float,c2=String,c3=String
sqoop import-all-tables
sqoop job --list
sqoop job --show visits  
sqoop job --create visits --meta-connect jdbc:hsqldb:hsql://metastore.example.com:16000/sqoop -- import --table visits  
sqoop import --connect jdbc:mysql://mysql.example.com/sqoop --username sqoop --password sqoop --table cities --hive-import --hive-partition-key day 
--hive-partition-value "2013-05-22"
  
Sqoop supports three different file formats; one of these is text, and the other two are binary. The binary formats are Avro and Hadoop’s SequenceFile.  

Prior to using it with Sqoop, make sure your desired codec is properly installed and configured across all nodes in your cluster.

If in the mapred-site.xml file, the property mapred.output.compress is set to false with the final flag, then Sqoop won’t be able to compress the output files even when you call it with the --compress parameter

The selected compression codec might have a significant impact on subsequent processing. Some codecs do not support seeking to the middle of the compressed file
without reading all previous content, effectively preventing Hadoop from processing the input files in a parallel manner. You should use a splittable codec for data that you’re planning to use in subsequent processing

Splittable 		Not Splittable
BZip2, LZO 		GZip, Snappy

Can Sqoop run faster? --direct
Sqoop has direct support only for MySQL and PostgreSQL
you will need to make sure that those native utilities are available on all of your Hadoop TaskTracker nodes.
As the native utilities usually produce text output, binary formats like SequenceFile or Avro won’t work. Also, parameters that customize the escape characters, type mapping, column and row delimiters, or the NULL substitution string might not be supported in all cases.

You’ll find that many of the import parameters can’t be used in conjunction with the import-all-tables tool. you can’t use the parameter --target-dir

Sharing the Metastore Between Sqoop Clients:
Sqoop’s metastore can easily be started as a service:	sqoop metastore

The free-form query import can’t be used in conjunction with the --warehouse-dir parameter.

It’s important not to use Hive’s warehouse directory (usually /user/hive/warehouse) for the temporary location, as it may cause issues with loading data in the second step.

You want to import data into Hive on a regular basis (for example, daily), and for that purpose your Hive table is partitioned. You would like Sqoop to automatically import data into the partition rather than only to the table. In order to take advantage of this functionality, you need to specify two additional parameters: --hive-partition-key, --hive-partition-value

Sqoop mandates that the partition column be of type STRING

Both the HBase table and the column family must exist prior to running the Sqoop import command. If you want Sqoop to create the table automatically, you’ll need to specify the parameter --create-hbasetable.

