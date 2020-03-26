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
             true place)))
       universe))

;;conditions used while numbering
(defn all-numbered?
  [universe]
  (if (= (count (filter (fn [place] (= (:distance place) nil)) universe)) 0)
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

(defn -main []
  (numbering world))

