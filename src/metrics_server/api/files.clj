(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))

(defn filterOnlyFiles [data]
  (filter #(false? (:directory %) ) data))

(defn filterOnlyNonExecutable [data]
  (filter #(false? (:executable %) ) data))

(defn -main [& args]
  (def OnlyFiles (filterOnlyFiles (get-files)))

  (def OnlyNonExecutable (filterOnlyNonExecutable OnlyFiles))

  (def UpdatedNames (map (fn [x] (update x :name #(if-not (re-matches #".*conf$" %) % (str (x :name) "ig")))) OnlyNonExecutable))

  (def midSize ( / (reduce + (map #(:size %) UpdatedNames)) (count UpdatedNames)))
  (def MidSizeOutputString (print-str "Mid Size is: " midSize))
  (println MidSizeOutputString))