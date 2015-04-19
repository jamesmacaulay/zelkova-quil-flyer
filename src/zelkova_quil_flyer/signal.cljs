(ns zelkova-quil-flyer.signal
  (:require [zelkova-quil-flyer.vector-math :as v]
            [jamesmacaulay.zelkova.signal :as z]
            [jamesmacaulay.zelkova.mouse :as mouse]))

(defn steering
  [state mouse-pos]
  (assoc-in state [:flyer :position] mouse-pos))

(defn app-signal
  [init-state]
  (z/reductions steering
                init-state
                mouse/position))
