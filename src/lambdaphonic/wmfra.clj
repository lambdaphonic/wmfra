(ns lambdaphonic.wmfra
  (use [overtone.live :refer :all]
       [mud.core]
       [lambdaphonic.overtone.helpers]
       [lambdaphonic.overtone.synths]
       [overtone.inst.drum])
  (require [shadertone.tone :as t]))

(comment
(def mtone (tone :amp 3 :freq (midi->hz (note :E1))))
(ctl mtone :gate 1)
(ctl mtone :gate 0)
)

(def metro (metronome 120))

(comment
(t/start-fullscreen "resources/electric.glsl" :textures [:overtone-audio])
(t/stop)
)

(comment
(recording-start "/Users/martingondermann/Music/filename.wav")
(recording-stop)
)

(do
  (def moria1 [:E4  :C4 :A3 :C4 :E4  :C4 :A3 :C4 :E4  :C4 :A3  :C4  :F4 :E4 :D4 :C4])
  (def moria2 [:D4  :B3 :G3 :B3 :D4  :B3 :G3 :B3 :D4  :B3 :G3  :B3  :E4 :D4 :C4 :B3])
  (def moria3 [:B3  :C4 :D4 :F4 :E4  :D4 :C4 :A3 :B3  :E3 :F#3 :G#3 :A3 :A3 :A3 :A3])
  (def moria4 [:A3  :C4 :E4 :C4 :A3  :C4 :E4 :C4 :A3  :C4 :E4  :F4  :E4 :D4 :C4 :B3])
  (def moria5 [:G#3 :B3 :D4 :B3 :G#3 :B3 :D4 :B3 :G#3 :B3 :D4  :F4  :E4 :D4 :C4 :B3])
  (def moria6 [:A3  :C4 :E4 :C4 :A3  :C4 :E4 :C4 :A3  :C4 :E4  :G4  :F4 :E4 :D4 :C4])
  (def moria7 [:B3  :C4 :D4 :F4 :E4  :D4 :C4 :A3 :B3  :E3 :F#3 :G#3 :A3 :A3 :A3 :A3])

  (def bmoria [:E2 :E2 :D2 :D2 :C2 :C2 :B1 :B1 :E2 :E2 :A1 :B1 :E2 :E2 :B1 :B1 :C2 :C2 :A1 :B1])
  (def bass-line-moria (atom bmoria))

  (def moria (atom  [moria1 moria2 moria1 moria2 moria1 moria3 moria4 moria5 moria6 moria7]))
  (def moria-current-part (atom (first @moria))))

(defn melody [metro t beat]
  (at t
    (let [next-beat (+ beat 1)
          next-t (+ t (mspb metro))]
      (beat-map metro t beat next-beat 0.25
        (fn [b]
          (ctl mtone :amp (cosr b 3 3 3/7))))
      (on-beat beat 2 2 #(do
                           (ctl mtone :freq (midi->hz (note (first @bass-line-moria))))
                           (reset! bass-line-moria (rotate 1 @bass-line-moria))))
      (dorun
        (map (fn [b n]
                (at (+ t (mspb metro (+ 1 b)))
                    (do
                      (short-tone :pan (cosr (+ beat b) 1 0 8) :dur 1/4 :nharm (cosr beat 10 12 40) :freq (midi->hz (note n)) :amp (cosr b (cosr b 0.5 0.5 3/7) 0.5 3/5)))))
              (range 0 1 1/4)
             (take 4 @moria-current-part)))
      (reset! moria-current-part (rotate 4 @moria-current-part))
      (on-beat beat 4 4 #(do
                           (reset! moria (rotate 1 @moria))
                           (reset! moria-current-part (first @moria))))
      (apply-by next-t #'melody [metro next-t next-beat]))))

(comment
(melody metro (atbeat metro 1) 0)
)

(defn drums [metro t beat]
  (at t
      (let [next-beat (+ beat 1)
            next-t (+ t (mspb metro))]

        (dub-kick)

        (beat-map metro t beat next-beat 0.25
                  (fn [b]
                    (soft-hat :amp (cosr b 0.1 0.1 3/7))))

        (at (+ t (mspb metro 0.5))
            (do
              (closed-hat)))

        (on-beat beat 2 2 #(do
                             (at (+ t (mspb metro 0.5))
                                 (do
                                   (dry-kick)
                                   (open-hat)))))
        (apply-by next-t #'drums [metro next-t next-beat]))))

(comment
(drums metro (atbeat metro 1) 0)
)

