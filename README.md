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
 Format : timestamp,uid1,x1,y1,uid2,x2,y2,floor<br />
* timestamp - DateTime when meeting occurs<br />
* uid1 - unique id of first person<br />
* x1 - x position of first person when meeting occurs<br />
* y1 - y position of first person when meeting occurs<br />
* uid2 - unique id of second person<br />
* x2 - x position of second person when meeting occurs<br />
* y2 - y position of second person when meeting occurs<br />
* floor - floor that meeting occurs<br />

Design Rationale<br />
------
1. Technology<br />
    * Programming language : Scala
    * Additional library
        * Joda Time : DateTime implementation library
        * Akka Stream : Reactive Stream library that provide
2. Algorithm performance<br />
    * Time complexity
    * Space complexity
3. Further improvement<br />
    * To support batch query
    * To support repeated query
    * To support infinite output stream