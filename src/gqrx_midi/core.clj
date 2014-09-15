(ns gqrx_midi.core
  (:require [aleph.tcp :refer :all]
            [lamina.core :refer :all]
            [gloss.core :refer :all]
            [overtone.live :refer :all]))

(def ch
  (wait-for-result
    (tcp-client {:host "localhost",
                 :port 7356,
                 :frame (string :utf-8 :delimiters ["\r\n"])})))

(defn set-frequency [freq]
  (enqueue ch (str "F" freq)))

(defn midi-to-freq [note]
  (let [freq (* 280000 note)]
    (set-frequency freq)
    (println "Note:" note " Freq:" freq)))

  (overtone.live/on-event [:midi :note-on]
            (fn [{note :note velocity :velocity}]
              (midi-to-freq note))
            ::note-handler)
