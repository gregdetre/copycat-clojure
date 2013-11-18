(ns copycat-clojure.core-test
  (:require [clojure.test :refer :all]
            [copycat-clojure.core :refer :all]))

;; run with 'lein test'

(deftest a-test
  (testing
   "Should pass"
   (is (= 1 1))
   ;; (is false)
   )
  )

