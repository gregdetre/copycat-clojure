(ns copycat-clojure.core-test
  (:require [clojure.test :refer :all]
            [copycat-clojure.core :refer :all]))

;; (load "formulas_test")

;; run with 'lein test'

(deftest a-test
  (testing
   "Should pass"
   (is (= 1 1))
   ;; (is (blah 2 2) 4)
   ;; (is false)
   )
  )

