(ns com.phronemophobic.clirun
  (:require
    [clojure.edn :as edn]
    [clojure.string :as str])
  (:import
    [clojure.lang ExceptionInfo]))


(set! *warn-on-reflection* true)

(defn- err
  ^Throwable [& msg]
  (ex-info (str/join " " msg) {:exec-msg true}))

(defn- requiring-resolve'
  ;; copied and modified from core to remove constraints on Clojure 1.10.x
  [sym]
  (if (nil? (namespace sym))
    (throw (err "Not a qualified symbol:" sym))
    (or (resolve sym)
      (do
        (-> sym namespace symbol require)
        (resolve sym)))))


(defn- read-args
  [args]
  (loop [[a & as] args
         read-args []]
    (if a
      (let [r (try
                (edn/read-string {:default tagged-literal} a)
                (catch Throwable _
                  (throw (err "Unreadable arg:" (pr-str a)))))]
        (recur as (conj read-args r)))
      read-args)))


(defn no-args []
  (prn "hi"))

(defn test-fn [& args]
  (prn "args:" args))

(defn -main
  [& args]
  (try
    (let [[f & args] (read-args args)]
      (when (nil? f)
        (throw (err "No function found on command line")))
      (let [resolved-f (requiring-resolve' f)]

        (if resolved-f
          (apply resolved-f args)
          (throw (err "Function not found:" f)))))
    (catch ExceptionInfo e
      (if (-> e ex-data :exec-msg)
        (binding [*out* *err*]
          (println (.getMessage e))
          (System/exit 1))
        (throw e)))))




