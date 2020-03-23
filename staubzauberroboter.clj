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




(loop [originindex 0
             origins (get-numbered (number-places-around setorigin n) n)  ;;for the very first NUMBERING (0 ---> 1)
             originslength (count origins)]
        (if (> (count origins) 0)         
          (recur (number-places-around (first o)) (n) (rest get-numbered n)))
        )   ;;NEXT ORIGIN... SAME DISTANCE






(def number
  (fn
    [universe
     number
     origins])
  (loop [u universe  ;;will be generated through each recurssive iteration (upon the previous one)
         n 1  ;;the first iteration will generate distances 1 unit ahead
         o origins]  
    (if (= all-numbered? false)  ;;MOST IMPORTANT CONDITION ...There are still unnumbered places?
      (loop [originindex 0
             origins (get-numbered (number-places-around setorigin n) n)  ;;for the very first NUMBERING (0 ---> 1)
             originslength (count origins)]
        (if (> (count origins) 0)         
          (recur (number-places-around (first o)) (n) (rest get-numbered n)))
        )   ;;NEXT ORIGIN... SAME DISTANCE
      (;;Give back the universe structure that was build until that moment


       )))

(recur (number-places-around (first o)) (+ n 1) (get-numbered n))  ;;origins must be a collections of points from which number-place-around must be done...NEXT DISTANCE
