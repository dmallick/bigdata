
Pre-requisite:
-----------------------------------------------------------------
Ensure Mysql is installed. Also, the database, tables are in place
Ensure SQOOP is installed and configured
Ensure Hcatalog is installed and configured
Ensure Hive is configured and create database(command: create database retail_db;)

Command:
---------------------------------------------------------------
sqoop import --connect jdbc:mysql://localhost:3306/retail_db \
--username root \
--password cloudera \
--table categories \
--hcatalog-database retail_db \
--hcatalog-table categories \
--create-hcatalog-table \
--hcatalog-storage-stanza "stored as orcfile" \
--outdir sqoop_import \
-m 1 \
--compression-codec org.apache.hadoop.io.compress.SnappyCodec \
--driver com.mysql.jdbc.Driver

Verifying:
-----------------------------
Connect to hive(command) : 
# hive shell
hive> use retail_db;
hive> show tables;
hive> select * from categories;
OK
1	2	Football
2	2	Soccer
3	2	Baseball & Softball
4	2	Basketball
5	2	Lacrosse
6	2	Tennis & Racquet
7	2	Hockey
8	2	More Sports
9	3	Cardio Equipment
10	3	Strength Training
11	3	Fitness Accessories
12	3	Boxing & MMA

 