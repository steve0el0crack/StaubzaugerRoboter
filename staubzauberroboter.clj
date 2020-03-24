(ns proyect1.staubzauberroboter
  (:gen-class))

;;basic variables
(def world-dims {:x 3 :y 3})

;;world created with all positive values
(defstruct place :x :y :state :distance :display)
(def initialworld (for [y (range (:y world-dims)) x (range (:x world-dims))] (struct place x y)))

;;origin seted
(def setorigin
  (assoc
   (nth initialworld (rand (+ (:y world-dims) 1)))
   :state "X"
   :distance 0))

;;to detect when it is origin, and not to touch it
(defn is-start?? [place]
  (if (and (= (:x place) (:x setorigin)) (= (:y place) (:y setorigin)))
    place)
  )


;;since every index is positive, and there are no diagonal movements. Its just a matter of math.
(defn number-places-around
  [start  
   universe
   distance]
  (map (fn [place] 
         (let [x-distance (Math/abs (- (:x place) (:x start)))
               y-distance (Math/abs (- (:y place) (:y start)))
               total-distance (+ x-distance y-distance)]
           (cond 
             (not (= (:distance place) nil)) place
             (= 1 total-distance) (assoc place :distance distance)
             (= 0 total-distance) start
             true place
             )  
           ))
       universe)
  )

;;numbering made manually
(def first-numbering (number-places-around setorigin initialworld 1))

(def second-numbering (number-places-around {:x 0 :y 0 :state nil :distance 1 :display nil} first-numbering 2))
(def third-numbering  (number-places-around {:x 2 :y 0 :state nil :distance 1 :display nil} second-numbering 2))
(def fourth-numbering (number-places-around {:x 1 :y 1 :state nil :distance 1 :display nil} third-numbering 2))


(def fifth-numbering (number-places-around {:x 0 :y 1 :state nil :distance 2 :display nil} fourth-numbering 3))
(def sixth-numbering (number-places-around {:x 2 :y 1 :state nil :distance 2 :display nil} fifth-numbering 3))


;;recursive tools in Clojure
(defn factorial [number]
  (loop [x number,
         result 1]
    (if (= x 0)
      result
      (recur (- x 1) (* x result)))
    )
  )
(factorial 10)
(< 1 10)
(nth [1 2 3] 0)
(count [1 2 3])




(defn all-numbered?
  [universe]
  (if (= (count (filter (fn [place] (= (:distance place) nil)) universe)) 0)
    true
    false)
  )

(defn get-numbered
  [universe
   distance]
  (let [d distance
        u universe]
    (filter (fn [place] (= (:distance place) d)) u)))

(get-numbered first-numbering 1)
(get-numbered sixth-numbering 3)


(all-numbered? first-numbering)
(all-numbered? sixth-numbering)


(def world-numbered 
  (loop
      [origin setorigin
       distance 1
       currentworld (number-places-around origin initialworld 1)
       recurcoords (get-numbered currentworld distance)]
    (if (all-numbered? currentworld)
      currentworld
      (recur (first recurcoords)
             (+ distance 1)
             (number-places-around (first recurcoords) currentworld (+ distance 1))
             (rest recurcoords))
      )
    ))



