(ns metrics-server.api.hardware
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-metrics-with-http-info
  "Get hardware metrics"
  []
  (call-api "/hardware" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-metrics
  "Get hardware metrics"
  []
  (:data (get-metrics-with-http-info)))

(defn filterByCpuTemp [data]
  (filter #(>= (:cpuTemp %) 2) data))

(defn -main [& args]
  (def filteredByCpuTemp (filterByCpuTemp (get-metrics)))

  (def midTemp ( / (reduce + (map #(:cpuTemp %) filteredByCpuTemp)) (count filteredByCpuTemp)))
  (def MidTempOutputString (print-str "Mid Temp is: " midTemp))
  (println MidTempOutputString)

  (def midLoad ( / (reduce + (map #(:cpuLoad %) filteredByCpuTemp)) (count filteredByCpuTemp)))
  (def MidLoadOutputString (print-str "Mid Load is: " midLoad))
  (println MidLoadOutputString)

  )

