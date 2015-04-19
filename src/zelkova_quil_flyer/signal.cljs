(ns zelkova-quil-flyer.signal
  (:require [zelkova-quil-flyer.vector-math :as v]
            [jamesmacaulay.zelkova.signal :as z]
            [jamesmacaulay.zelkova.mouse :as mouse]))

(defn steering
  [state mouse-pos]
  (let [flyer-pos (-> state :flyer :position)
        diff      (v/subtract mouse-pos flyer-pos)
        angle     (v/vector->radians diff)]
    (assoc-in state [:flyer :angle] angle)))

(defn app-signal
  [init-state]
  (z/reductions steering
                init-state
                mouse/position))
