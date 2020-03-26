# StaubzaugerRoboter

THE PROBLEM
One "Hacking night" was asked to us, to programm a "StaubzauberRoboter" (Cleaner Roboter) that could leave a randomized room filled with obstacles (random too), total clean. There were 3 groups in total, and the solutions were following: The first one came up with nothing, were were the second and only reached to programm a random movement hoping the roboter could get to clean every dirty spot (lasted too long), and the last group had a maybe-good solution ending up cleaning a considerable portion of the room, but its movement did not seen intelligent at all.
On the other hand, we actually had a good solution but it took too long for Florian and me to implement it in Java, because of the platform we were working in and the lack of facilities of the language itself (in comparison with Python or Clojure). The challenge originally sounded a little bit more complex than those we had faced, therefore the first step was to turn it into smaller challenges that we knew we could handle. In order to do it, we had to separate the elements involved: The roboter, the world (room), the concept of moving and cleaning, the implications of barriers and obstacles, and finally very movement logic itself (because we assumed from the beginning that there were more than one way of moving).  
'''
The first step of the original idea was to divide the field into a certain amount places,so that after the division we could tell which of these "places" were barriered (obstacle and therefore not to clean) from those who were dirty.
'''
