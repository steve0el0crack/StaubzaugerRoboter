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

;;conditions used while numbering
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

;;///////////////////SOLVED BY LAYERS//////////////////////////////
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
               (number-places-around (nth origins (+ index 1)) currentworld d))
        ))))

(def first-layer (partial nth-layer 1))
(def first-layer-ready
  (first-layer world [setorigin]))

(def second-layer (partial nth-layer 2))
(def second-layer-ready
  (second-layer (number-places-around setorigin initialworld 1) (get-numbered first-numbering 1)))

(def third-layer (partial nth-layer 3))
(def third-layer-ready
  (third-layer second-layer-ready (get-numbered second-layer-ready 2))) 





