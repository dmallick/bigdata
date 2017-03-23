Oozie
--------
They can do a number of different things: execute a MapReduce job, run a Java main class, or run a Hive or Pig script, to name a few possibilities
Scheduling Recurring Workflows
Oozie has another type of a job called a coordinator application. Coordinator applications allow users to schedule more complex workflows, including workflows that are scheduled regularly,or that have dependencies on the output from other workflows.

Oozie Coordinator 
=================
Oozie Coordinator is a collection of predicates (conditional statements based on time-frequency and data availability) and actions (i.e. Hadoop Map/Reduce jobs, Hadoop file system, Hadoop Streaming, Pig, Java and Oozie sub-workflow). Actions are recurrent workflow jobs invoked each time predicate returns true. 

Oozie version 2 and higher supports Coordinator Jobs. Coordinator Job is defined in the XML Process Definition Language. 

Predicates are conditional statements, defined using attributes “interval, start-time and end-time” for time-based triggering and xml-tags “dataset and input-events” for data-availability based triggering of workflows. 
Actions are the mechanism by which a workflow is triggered for the execution of a computation/processing task. Action contains description of one or more workflows to be executed.

Oozie is lightweight as it uses existing Hadoop Map/Reduce framework for executing all tasks in a workflow. This approach allows it to leverage existing Hadoop installation for providing scalability, reliability, parallelism, etc.
On the basis of functionality, Coordinator can be sub-divided into two major groups [2]:

1. Time-Based Coordinator: This type of Coordinator definition is used for invoking the workflow repeatedly after an interval between a specified period of time.

2.File-Based Coordinator: This type of Coordinator definition is used for invoking the workflow on the basis of data availability and data polling.

2.1  Simple File-Based Coordinator: The action is invoked whenever data available predicate is true.

2.2 Sliding Window-Based Coordinator:  It is invoked frequently and data is aggregated over multiple overlapping previous instances. For example, invoking it at a frequency of 5 minutes and running action on aggregated previous 4 instances of 15 minutes data.

2.3Rollups-Based Coordinator: It is invoked after a long period of time and data is aggregated over multiple previous instances from last time of invocation. For example, it will run once a day, and will trigger a workflow that aggregates 24 instances of hourly data.
