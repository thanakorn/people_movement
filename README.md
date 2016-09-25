# people_movement

Running Instructions<br />
------
1. Install Scala (http://www.scala-lang.org/download/)<br />
2. Install Scala Build Tool(SBT) (Mac: http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Mac.html, Windows: http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Windows.html)<br />
3. clone project from https://github.com/thanakorn/people_movement.git<br />
4. cd <**_project_path_**><br />
5. sbt compile<br />
6. sbt "run <**_uid1_**> <**_uid2_**>"<br />

Output Description<br />
------
 Format : *timestamp,uid1,x1,y1,uid2,x2,y2,floor*<br />
* *timestamp* - DateTime when meeting occurs<br />
* *uid1* - unique id of first person<br />
* *x1* - x position of first person when meeting occurs<br />
* *y1* - y position of first person when meeting occurs<br />
* *uid2* - unique id of second person<br />
* *x2* - x position of second person when meeting occurs<br />
* *y2* - y position of second person when meeting occurs<br />
* *floor* - floor that meeting occurs<br />

Workflow Description<br />
------
1. Load dataset from file
2. Preprocess data
    1. Remove trace that uid doesn't match query's uid
    2. Normalize timestamp by removing millisecond
3. Calculation
    1. Group trace by floor id
    2. For each floor id, create trace list by uid grouping
    3. Generate all possible trace list pairs in each floor
    4. For each trace list pair, find meetings if exist
    5. Log all meetings occur into output file

Algorithm<br />
------
* Pseudo code :
```
function findMeeting(t1, t2, maxMeetingDistance)
    Description :
        Find meeting between two list of trace
    Definitions :
        Trace : (timestamp, uid, position, floor, uid)
        Meeting : (timestamp, uid1, position1, uid2, position2, floor)
    Input :
        t1 and t2 : Two arrays of Trace t1 and t2
        maxMeetingDistance : maximum distance between position to consider as meeting
    Output :
        Array of meeting
    if (t1 and t2 is overlap)
        begin <- minTimestamp(minTimestamp(t1), minTimestamp(t2))
        end <- maxTimestamp(maxTimestamp(t2), maxTimestamp(t2))
        meetings <- empty list
        for t in t1 when begin <= t.timestamp
            for s in t2 when begin <= s.timestamp <= end
                if (t.timestamp = s.timestamp and t.floor = s.floor and distance(t.position, s.position) <= maxMeetingDistance)
                    meetings.add(createMeeting(t1, t2))
        return meetings
    else return empty
```
* Time complexity : ```O(n ^ 2)```
* Space complexity : ```O(n)```

Techonology
------
* Programming language : Scala
* External library :
    * Joda DateTime - Java-based date and time API
    * Akka Stream - Implementation of Reactive Stream

Further improvement
------
* Large batch query
    * Using concurrent processing : Since each query is independence from other, higher concurrent processing leads to better performance.
    One approach that can be used to tackle this problem is using distributed computing by distribute data and queries into multiple nodes.
    For example, spreading queries to message queue(Kafka, RabbitMQ etc.) and having multiple consumers to fetch queries and do calculation then write to central data storage.
* Repeated query
    * Using data storage : As results of each query never change, there is no point to recalculate same query again. Every time that system receive
    unseen query it should save query's result into external data storage, such as database or cache, so that it can reuse if same query was sent in the future.
* Stream of input
    * Akka stream is used in this project therefore there are only few changes require to support stream of input.
        * 1. Change type of source to support input stream : In current version, Source is created from the file which is static. This need to be change to other type for supporting stream processing.
        * 2. Each step of flow should be asynchronous : Sine stream processing allow system to manipulate multiple chunk of data concurrently, therefore each processing step should be done as asynchronous as possible.