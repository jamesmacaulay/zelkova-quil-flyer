(ns ^:figwheel-always zelkova-quil-flyer.core
    (:require [quil.core :as q :include-macros true]
              [quil.middleware]
              [jamesmacaulay.zelkova.signal :as z]
              [zelkova-quil-flyer.signal :as signal]))

(enable-console-print!)

(defn setup
  []
  (let [init-state {:flyer {:position [250 250]
                            :angle    0
                            :velocity [0 0]
                            :image (q/request-image "flyer.png")}}]
    (-> init-state
        (signal/app-signal)
        (z/pipe-to-atom (q/state-atom)))
    (q/frame-rate 30)
    (q/no-smooth)
    (q/rect-mode :center)
    (q/shape-mode :center)
    (q/image-mode :center)
    init-state))

(defn draw-flyer
  [{:keys [image position angle]}]
  (q/reset-matrix)
  (q/fill 255)
  (q/stroke 180)
  (apply q/translate position)
  (q/rotate (+ angle q/HALF-PI))
  (q/scale 0.2)
  (q/image image 0 0))

(defn draw
  [{:keys [flyer]}]
  (q/background 20 20 40)
  (draw-flyer flyer))

(q/defsketch flyer
  :size [800 600]
  :setup setup
  :draw draw
  :middleware [quil.middleware/fun-mode])
