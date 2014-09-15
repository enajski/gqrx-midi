(ns gqrx_midi.core
  (:require [aleph.tcp :refer :all]
            [lamina.core :refer :all]
            [gloss.core :refer :all]
            [overtone.live :as live]))

(def ch
  (wait-for-result
    (tcp-client {:host "localhost",
                 :port 7356,
                 :frame (string :utf-8 :delimiters ["\r\n"])})))

(defn set-frequency [freq]
  (enqueue ch (str "F" freq))
  (println "Freq:" freq))

(def midi-min 36)
(def midi-max 84)
(def hackrf-min-freq 10e6)
(def hackrf-max-freq 6e9)

(defn percentage-in-range [val minimum maximum]
  (let [top    (- val minimum)
        bottom (- maximum minimum)]
    (/ top bottom)))

(defn map-midi-to-freq [note min-freq max-freq]
  (float
    (+ min-freq
       (* (percentage-in-range note midi-min midi-max)
          (- max-freq min-freq)))))

(live/on-event [:midi :note-on]
  (fn [{note :note velocity :velocity}]
    (set-frequency
      (map-midi-to-freq
         note
         hackrf-min-freq
         hackrf-max-freq)))
  ::note-handler)
