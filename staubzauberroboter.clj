(ns proyect1.staubzauberroboter
  (:gen-class))

(def world-dims {:x 3 :y 3})

(defstruct place :x :y :state :distance :display)

(def world (for [y (range (:y world-dims)) x (range (:x world-dims))] (struct place x y)))

(def setorigin
  (assoc
   (nth world (rand (+ (:y world-dims) 1)))
   :state "X"
   :distance 0))


