Oozie Coordinator:

$ oozie job -oozie http://localhost:11000/oozie -config /appl/oozie-appl/conf/coordinatorjob.properties -run
coordinatorjob.properties
coordinator.xml
java jar file having main method
All the jar files have to be in the "lib" directory in the same hirarchy as your coordinator.xml

http://developeriq.in/articles/2013/aug/28/scheduling-workflows-using-oozie-coordinator/
https://www.tutorialspoint.com/apache_oozie/apache_oozie_coordinator.htm
https://community.hortonworks.com/articles/27497/oozie-coordinator-and-based-on-input-data-events.html

http://blog.cloudera.com/blog/2013/11/how-to-shorten-your-oozie-workflow-definitions/
https://blog.cloudera.com/blog/2013/01/how-to-schedule-recurring-hadoop-jobs-with-apache-oozie/
