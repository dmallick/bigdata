Hive More Info
===========================

https://blog.cloudera.com/blog/2013/01/how-to-schedule-recurring-hadoop-jobs-with-apache-oozie/
----------------------------------------------------------------------------------------------
CREATE EXTERNAL TABLE tweets (
 ...
 retweeted_status STRUCT<
   text:STRING,
   user:STRUCT>,
 entities STRUCT<
   urls:ARRAY>,
   user_mentions:ARRAY>,
   hashtags:ARRAY>>,
 text STRING,
 ...
)
PARTITIONED BY (datehour INT)
ROW FORMAT SERDE 'com.cloudera.hive.serde.JSONSerDe'
LOCATION '/user/flume/tweets';

In order to add a partition to a Hive table, you’ll need a file containing the queries that need to be executed. In Hive, partitions are created through an ALTER TABLE statement.

In order to run a recurring job, you’ll need to have an Oozie workflow to execute. 
