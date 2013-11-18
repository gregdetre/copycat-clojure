(ns copycat-clojure.core
  ;; (:use [copycat-clojure.formulas :only [blah]]
  (:use [copycat-clojure.formulas]))

;; e.g.
;; (ns examples.ns
;;   (:refer-clojure :exclude [next replace remove])
;;   (:require (clojure [string :as string]
;;                      [set :as set]) [clojure.java.shell :as sh])
;;   (:use (clojure zip xml))
;;   (:import java.util.Date
;;            java.text.SimpleDateFormat (java.util.concurrent Executors
;;                                                             LinkedBlockingQueue)))


;; call this in a helper .clj
;; (in-ns copycat-clojure.core)


;; (load "formulas")
;; (load "my_utils")
;; (load "run")
;; (load "lucid-util")
;; (load "lucid-util")
;; (load "workspace-structures")


(defn -main
  "Application entry point"
  [& args]
  ;; (println (blah 1 2))
  (println "main")
  )
