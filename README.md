# StaubzaugerRoboter

THE PROBLEM
In a "Hacking night" it was asked to us, to programm a "StaubzauberRoboter" (Cleaner Roboter) that could leave a randomized room filled with obstacles (random too), total clean. There were 3 groups in total, and the solutions were following: The first one came up with nothing, we were the second and we only reached to programm a random movement hoping the roboter could get to clean every dirty spot (lasted too long), and the last group had a maybe-good solution ending up cleaning a considerable portion of the room, but its movement did not seen intelligent at all.
On the other hand, we actually had a good solution but it took too long for Florian and me to implement it in Java, because of the platform we were working in (Greenfoot & BlueJ) and the lack of facilities of the language itself (in comparison with Python or Clojure). We could divide the challenge into tiny parts. In order to do it, we had to separate the elements involved: The roboter, the world (room), the concept of moving and cleaning, the implications of barriers and obstacles, and finally the very movement logic itself.
With this last element we must be carefull, because there are actually a lot of ways in which an "AGENT" -in this case a robot- can behave. The random-decision-movement was the easiest, but we came up with the following: The robot numbers the entire field with the distance from his position to the square, and when every square has already a number; he goes to the furthest square (the greatest number) and then to the lower. This process repeats itself till every square are cleaned up. The solution was implemented in both languages, completely and more worked in the Java version by Florian; and partially and only structurally -no UI- on Clojure. But this problem can lead us to more challenging concepts... like asynchrony and concurrency!
In Clojure, the concepts of an agent, an atom and ref were used to bring the challenge into a new level: Agent based simulation. Now the question is more complicated... Which can be the most efficient "thought-process" for each agent individually, so that as a group they clean the world the fastest possible? Some data analysis will be made in order to answer this question: How many times a square was cleaned repeatedly? How many squares did each of the robots cleaned? What kind of information could be relevant for determining the direction of the robot based on the actions of the others? 
Maybe we can reach another level...
