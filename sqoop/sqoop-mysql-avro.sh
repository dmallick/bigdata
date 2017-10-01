sqoop import --table orders --connect "jdbc:mysql://quickstart.cloudera:3306/retail_db" --username retail_dba --password cloudera -m 1 --target-dir /user/cloudera/problem7/prework --as-avrodatafile
