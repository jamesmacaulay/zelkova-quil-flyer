(ns zelkova-quil-flyer.vector-math)

(defn magnitude
  [v]
  (let [sum-of-squares (reduce + (map #(. js/Math (pow % 2)) v))]
    (. js/Math (sqrt sum-of-squares))))

(defn normalize
  [v]
  (mapv #(/ % (magnitude v)) v))

(defn- map-numbers-as-sequences
  [vs]
  (map (fn [v]
         (if (number? v)
           (repeat v)
           v))
       vs))

(defn multiply
  [& vs]
  (apply mapv * (map-numbers-as-sequences vs)))

(defn add
  [& vs]
  (apply mapv + (map-numbers-as-sequences vs)))

(defn subtract
  [& vs]
  (apply mapv - (map-numbers-as-sequences vs)))

(defn with-magnitude
  [m v]
  (mapv (partial * m) (normalize v)))

(defn limit-magnitude
  [max v]
  (if (> (magnitude v) max)
    (with-magnitude max v)
    v))

(defn radians->vector
  [angle]
  [(. js/Math (cos angle))
   (. js/Math (sin angle))])

(defn vector->radians
  [[x y]]
  (. js/Math (atan2 y x)))
