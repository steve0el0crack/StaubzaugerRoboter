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



;;//////////////////////numbering made manually///////////////////

;;FIRST LAYER
;;first we need coords that will act like origins for the numbering. This will be later not one, but a collection of origins to be iterated
{:x 0, :y 1, :state "X", :distance 0, :display nil}
(def first-numbering (number-places-around setorigin initialworld 1))

(all-numbered? first-numbering)  ;;Then must be checked after that numbering, if everything is numbered
(get-numbered first-numbering 1)  ;;In case there are more places to number, we should define the next origin(s) in order to continue numbering the world
;;In case the index's originally passed (at the beginning) were already consumed...then we must repeat the cycle with NEW ORIGINS and a NEW DISTANCE to set.
({:x 0, :y 0, :state nil, :distance 1, :display nil} {:x 1, :y 1, :state nil, :distance 1, :display nil} {:x 0, :y 2, :state nil, :distance 1, :display nil}) 

;;SECOND LAYER
(def second-numbering (number-places-around {:x 0 :y 0 :state nil :distance 1 :display nil} first-numbering 2))
;;The distance will not be changed, but the ORIGIN from which the numbering must be made.
(def third-numbering  (number-places-around {:x 2 :y 0 :state nil :distance 1 :display nil} second-numbering 2))
(def fourth-numbering (number-places-around {:x 1 :y 1 :state nil :distance 1 :display nil} third-numbering 2))
;;Once the ORIGIN's for this layer are consumed again, NEW ORIGINS must be provided and a NEW DISTANCE
(get-numbered fourth-numbering 2)
({:x 1, :y 0, :state nil, :distance 2, :display nil} {:x 2, :y 1, :state nil, :distance 2, :display nil} {:x 1, :y 2, :state nil, :distance 2, :display nil})

;;THIRD LAYER
(def fifth-numbering (number-places-around {:x 0 :y 1 :state nil :distance 2 :display nil} fourth-numbering 3))
(def sixth-numbering (number-places-around {:x 2 :y 1 :state nil :distance 2 :display nil} fifth-numbering 3))
(get-numbered sixth-numbering 3)
(all-numbered? sixth-numbering)
;;YES
;;RETURN CURRENTWORLD !!


;;//////////////////////////////////////////////////////////////////

(def second-layer
  (fn [initialworld
       origins]
    (loop
        [index 0
         origin (nth origins index)
         distance 2
         currentworld (number-places-around origin initialworld distance)]
      (if (= index (- (count origins) 1))
        currentworld
        (recur (+ index 1)
               (nth origins (+ index 1))
               distance
               (number-places-around (nth origins (+ index 1)) currentworld distance))
        ))))

(def third-layer
  (fn [initialworld
       origins]
    (loop
        [index 0
         origin (nth origins index)
         distance 3
         currentworld (number-places-around origin initialworld distance)]
      (if (= index (- (count origins) 1))
        currentworld
        (recur (+ index 1)
               (nth origins (+ index 1))
               distance
               (number-places-around (nth origins (+ index 1)) currentworld distance))
        ))))

(def second-layer-ready
  (second-layer (number-places-around setorigin initialworld 1) (get-numbered first-numbering 1))) 
(def third-layer-ready
  (third-layer second-layer-ready (get-numbered second-layer-ready 2))) 





