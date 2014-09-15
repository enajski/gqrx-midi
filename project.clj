(defproject gqrx-midi "0.0.1-SNAPSHOT"
  :description "MIDI interface for the GQRX SDR app"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [aleph "0.3.3"]
                 [overtone "LATEST"]]
  :profiles {:dev {:dependencies [[midje "1.5.0"]]}})
