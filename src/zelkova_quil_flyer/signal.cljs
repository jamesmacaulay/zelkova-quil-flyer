(ns zelkova-quil-flyer.signal
  (:require [zelkova-quil-flyer.vector-math :as v]
            [jamesmacaulay.zelkova.signal :as z]
            [jamesmacaulay.zelkova.mouse :as mouse]
            [jamesmacaulay.zelkova.keyboard :as keyboard]
            [jamesmacaulay.zelkova.time :as time]))

(defn thrust
  [state toggle]
  (if toggle
    (let [angle (-> state :flyer :angle)]
      (update-in state
                 [:flyer :position]
                 (fn [pos]
                   (->> angle
                        (v/radians->vector)
                        (v/add pos)))))
    state))

(defn steering
  [state mouse-pos]
  (let [flyer-pos (-> state :flyer :position)
        diff      (v/subtract mouse-pos flyer-pos)
        angle     (v/vector->radians diff)]
    (assoc-in state [:flyer :angle] angle)))

(defn app-signal
  [init-state]
  (let [time-deltas (time/fps 30)
        inputs (z/map vector
                      time-deltas
                      keyboard/space
                      mouse/position)]
    (z/reductions (fn [state [time-delta spacebar? mouse-pos]]
                    (-> state
                        (thrust spacebar?)
                        (steering mouse-pos)))
                  init-state
                  inputs)))
