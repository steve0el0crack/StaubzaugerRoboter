(ns StaubzaugerRoboter.staubzauberroboter
  (:gen-class))

;; Basic variable... dimension of the world. Cases in which the world is not cuadrangular, are not taken into account.
;; They are coming soon!
(def dim 3)
(def n-robots 2)

;; The structure of the world is a nested list of REF's.
(def world
  (apply vector (map (fn [y]
                       (apply vector (map (fn [x]
                                            (ref {}))  ;; later will be added :R of ROBOT.
                                          (range dim))))
                     (range dim))))

;;I will not dereference the atom here because I want the ATOM itself, and not the value
(defn get-place [[x y]]
  (-> world
      (nth y)
      (nth x)))

;; Imitating create-ant of ant.clj of Rich Heckey. But here comes something new and it is my first "Race Condition": I am creating the robots and placing them complete random all over the world. If I make this process one at a time, there will be no problem. But if instead I use a PMAP in order to represent better the fact that that all these robots are gonne be "thrown" into the world and this must be at the same time. In that case I would have my first BIG problem in other languages, but in Clojure there are REF's ATOM's and AGENT's and those tools are great.
;; Two functions on separate threads are gonna try to change the state of the same place (to put some Robot in) and since the thing to be changed is not just merely a primitive value, but a REF. This REF will change his state atomically, that means it will accept only one of the robots and as soon as this robot has moved on to the next REF (position), the other will come into play. This happens because the function will be tried a lot of times and only when some condition of mine mantains throug time, the change will be applied. 
(defn create-robot []
  (let [counter 0
        x (rand-int dim)
        y (rand-int dim)
        place (get-place [x y])]
    (sync nil 
          (alter place assoc :R 0)  ;; Similarly for the process of rendering, and changes happening "on the board", I do need some flag or distinction for those places filled already with some Robot.
          (agent [x y]))))  ;; At the end I just need a list of agents containing the only data that really matters: The position of the Robot. Because is actually this data that will vary through time, and not other. The timing must be in relate to this.

(def robots (apply vector (map (fn [_] (create-robot)) (range n-robots))))  

(flatten (for [a  world]
           (for [b a]
             @b)))

(def origin
  (let [x (rand-int dim)
        y (rand-int dim)]
    (swap!
     (get-place [x y])
     assoc :R "X" :distance {"X" 0})
    [x y]))


;;to detect when it is origin, and not to touch it
(defn is-start? [place]
  (if (and (= (:x place) (:x origin)) (= (:y place) (:y setorigin)))
    place))

(defn get-places-around [[x y]]
  (let [xindex (- (:x world-dims) 1)
        yindex (- (:y world-dims) 1)]
    (remove (fn [coord] (or (> (first coord) xindex)
                            (> (second coord) yindex)
                            (some (fn [axis] (< axis 0)) coord)))
            (map (fn [delta]
                   (let [dx (first delta)
                         dy (second delta)]
                     [(+ x dx) (+ y dy)]))
                 [[1 0]
                  [-1 0]
                  [0 1]
                  [0 -1]]))))

;;since every index is positive, and there are no diagonal movements. Its just a matter of math.
(defn number-places-around
  [start  
   distance]
  (map (fn [coord]
         (let [place (get-place coord)]
           (when-not (some (fn [a] (= a "X")) (keys (:distance @place)))
             (swap! place update :distance assoc "X" distance))))
       (get-places-around start)))

(number-places-around origin 1)
(get-places-around origin)
(get-place origin)
world

;;conditions used while numbering
(defn all-numbered-by?
  [ID]
  (if (= (count (remove
                 (fn [atom] (some (fn [key] (= key ID))
                                  (keys (:distance @atom))))
                 world)) 0)
    true
    false))



(defn get-numbered
  [universe
   distance]
  (let [d distance
        u universe]
    (filter (fn [place] (= (:distance place) d)) u)))

(def nth-layer
  (fn [distance
       initialworld
       origins]
    (loop
        [index 0
         origin (nth origins index)
         d distance
         currentworld (number-places-around origin initialworld d)]
      (if (= index (- (count origins) 1))
        currentworld
        (recur (+ index 1)
               (nth origins (+ index 1))
               distance
               (number-places-around (nth origins (+ index 1)) currentworld d))))))

(defn numbering
  [initialworld]
  (loop [currentworld initialworld
        distance 1
        origins [setorigin]]
    (if (all-numbered? currentworld)
      currentworld
      (recur (nth-layer distance currentworld origins)
             (+ distance 1)
             (get-numbered (nth-layer distance currentworld origins) distance)))))

(def numbered-world (numbering world))


(def a (atom []))

(pmap
 (fn [x]3
   (swap! a conj x))
 (range 10))

(conj [] 1)

(shutdown-agents)


;;******************************************* UI *******************************8

(import
 '(java.awt Dimension Color Graphics)
 '(javax.swing JPanel JFrame)
 '(java.awt.image BufferedImage))

(def pixels 500)
(def scale (/ pixels dim))

;; The color system used for the process of rendering is RGB, that menas RED, GREEN and BLUE.
;; The goal is that at least 1 of the robots run over each place, so that at the end every place
;; should have been runned over at least one time. Therefore, the initial value of the REF's
;; (places) will be just nothing "{}". If there is a robot there, then "{R : T}" where T stands
;; for the number of places THAT robot has already cleaned up, that will RED. Right after being
;; on that place, the new value of the place will be "{Clean : T}" where T stands for the times
;; this place has been runned over by any of the robots, this will be GREEN. The greater T becomes
;; , the more green it will be. And finally a place will be BLACK if it is a BLOCK, "{}"

(defn render-place  ;; "{}" WTHIE "{R : T}" RED "{Clean : T}" GREEN "{Block : nil}" BLACK
  [bg x y v]  ;; the color depends on what is in the place at that moment, the value of the ref.
  (let [white (new Color 255 255 255)
        green (new Color 0 255 0)
        red (new Color 255 0 0)
        black (new Color 0 0 0)] 
    (doto bg
      (.setColor (case (first (keys v))
                   nil white
                   "Clean" green
                   "Block" black
                   :R red))
      (.fillRect (* x scale) (* y scale)
                 (* (+ x 1) scale) (* (+ y 1) scale)))))


(defn render
  [g]
  (let [img (new BufferedImage pixels pixels (. BufferedImage TYPE_INT_ARGB))
        bg (. img (getGraphics))
        values (dosync (apply vector (for [x (range dim)
                                           y (range dim)]
                                       @(get-place [x y]))))]
    (dorun (for [y (range dim)
                 x (range dim)]
             (render-place bg x y (nth values (+ (* y dim) x)))))
    (. g (drawImage img 0 0 nil))
    (. bg (dispose))))

(def panel (doto (proxy [JPanel] []
                   (paint [g] (render g)))
             (.setPreferredSize (new Dimension pixels pixels))))

(def frame (doto (new JFrame) (.add panel) .pack .show))

