(ns proyect1.staubzauberroboter
  (:gen-class))

;;basic variables
(def world-dims {:x 3 :y 3})

;;world created with all positive values
(defstruct place :x :y :state :distance :display)
(def world (for [y (range (:y world-dims)) x (range (:x world-dims))] (struct place x y)))

;;origin seted
(def setorigin
  (assoc
   (nth world (rand (+ (:y world-dims) 1)))
   :state "X"
   :distance 0))

;;to detect when it is origin, and not to touch it
(defn is-start?? [place]
  (if (and (= (:x place) (:x setorigin)) (= (:y place) (:y setorigin)))
    place)
  )


;;since every index is positive, and the movements are only diagonal. Its just a matter of math.
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

(defn factorial [number]
  (loop [x number,
         result 1]
    (if (= x 0)
      result
      (recur (- x 1) (* x result)))
    )
  )

(factorial 10)


(def first-numbering (number-places-around setorigin world 1))
(def second-numbering (number-places-around {:x 0 :y 0 :state nil :distance 1 :display nil} first-numbering 2))

  


(= 0 nil)
 

(filter (fn [place] (if = (:x place) (:x setorigin))) world)


;;for making the numbering, we need the places to number!
(defn first-step-to-numbering
  [coords] ;;coords are {:x _ :y _ }
  (let [x (:x coords)
        y (:y coords)]
    (filter ()
            world)))  
