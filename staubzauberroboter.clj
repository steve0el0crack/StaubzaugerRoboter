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
(def first-numbering (number-places-around setorigin world 1))
({:x 0, :y 0, :state nil, :distance 1, :display nil} {:x 1, :y 0, :state "X", :distance 0, :display nil} {:x 2, :y 0, :state nil, :distance 1, :display nil}
 {:x 0, :y 1, :state nil, :distance nil, :display nil} {:x 1, :y 1, :state nil, :distance 1, :display nil} {:x 2, :y 1, :state nil, :distance nil, :display nil}
 {:x 0, :y 2, :state nil, :distance nil, :display nil} {:x 1, :y 2, :state nil, :distance nil, :display nil} {:x 2, :y 2, :state nil, :distance nil, :display nil})

(def second-numbering (number-places-around {:x 0 :y 0 :state nil :distance 1 :display nil} first-numbering 2))
(def third-numbering  (number-places-around {:x 2 :y 0 :state nil :distance 1 :display nil} second-numbering 2))
(def fourth-numbering (number-places-around {:x 1 :y 1 :state nil :distance 1 :display nil} third-numbering 2))
({:x 0, :y 0, :state nil, :distance 1, :display nil} {:x 1, :y 0, :state "X", :distance 0, :display nil} {:x 2, :y 0, :state nil, :distance 1, :display nil}
 {:x 0, :y 1, :state nil, :distance 2, :display nil} {:x 1, :y 1, :state nil, :distance 1, :display nil} {:x 2, :y 1, :state nil, :distance 2, :display nil}
 {:x 0, :y 2, :state nil, :distance nil, :display nil} {:x 1, :y 2, :state nil, :distance 2, :display nil} {:x 2, :y 2, :state nil, :distance nil, :display nil})

(def fifth-numbering (number-places-around {:x 0 :y 1 :state nil :distance 2 :display nil} fourth-numbering 3))
(def sixth-numbering (number-places-around {:x 2 :y 1 :state nil :distance 2 :display nil} fifth-numbering 3))
({:x 0, :y 0, :state nil, :distance 1, :display nil} {:x 1, :y 0, :state "X", :distance 0, :display nil} {:x 2, :y 0, :state nil, :distance 1, :display nil}
 {:x 0, :y 1, :state nil, :distance 2, :display nil} {:x 1, :y 1, :state nil, :distance 1, :display nil} {:x 2, :y 1, :state nil, :distance 2, :display nil}
 {:x 0, :y 2, :state nil, :distance 3, :display nil} {:x 1, :y 2, :state nil, :distance 2, :display nil} {:x 2, :y 2, :state nil, :distance 3, :display nil})


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

(defn all-numbered?
  [universe]
  (if (= (count (filter (fn [place] (= (:distance place) nil)) universe)) 0)
    true
    false)
  )


(def number
  (fn
    
    [universe
     number])
  (loop []
    (if (= all-numbered? false)
      (recur )  ;;There are still unnumbered places
      universe))  ;;Give back the universe structure that was build until that moment
  )


