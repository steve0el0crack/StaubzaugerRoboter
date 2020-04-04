(ns StaubzaugerRoboter.staubzauberroboter
  (:gen-class))

;;basic variables
(def world-dims {:x 3 :y 3})
;;world created with all positive values
(def world
  (apply vector (map (fn [y]
                       (apply vector (map (fn [x]
                                            (ref {}))
                                          (range (:x world-dims)))))
                     (range (:y world-dims)))))

(defn get-place [[x y]]
  (-> world
      (nth y)
      (nth x)))  ;;I will not dereference the atom here because I want the ATOM itself, and not the value

(defn add-robot
  [place robot]
  (cond
    (some (fn [key] (= :R key)) (keys @place)) "BESETZT"  ;;if there is already a robot there... may later be if an obstacle is present.
    :else (dosync
           (alter place assoc :R robot))))

(defn create-robot
  [ID]
  (let [x (rand-int (:x world-dims))
        y (rand-int (:y world-dims))]
    (add-robot (get-place [x y]) (agent {ID [x y]}))
    [x y]))

(def robots (doall (map create-robot ["X" "Y"])))  ;;The problem here comes when more than one robot is assigned the same place. I must be able to manage it so, that only one gets to the actual place, and the rest waits until it is disoccupied.

(map (fn [coord] (get-place coord)) robots)

;;origin
(def origin
  (let [x (rand-int (:x world-dims))
        y (rand-int (:y world-dims))]
    (swap!
     (get-place [x y] )
     assoc :R "X" :distance {"X" 0})
    [x y]))


;;to detect when it is origin, and not to touch it
(defn is-start? [place]
  (if (and (= (:x place) (:x setorigin)) (= (:y place) (:y setorigin)))
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
 (fn [x]
   (swap! a conj x))
 (range 10))

(conj [] 1)

(shutdown-agents)
