(ns copycat-clojure.formulas-test
  (:use [clojure.test]
        [copycat-clojure.formulas]))


;; (deftest test-update-temperature
;;   (testing
;;    (is false)))


(deftest test-temperature-adjusted-value
  (testing "based on running the python code with sample values"
           (is (=ish (temperature-adjusted-value 100 10) 3.1622776601683795 1.000001))
           (is (=ish (temperature-adjusted-value 50 0) 0.0 1.000001))
           (is (=ish (temperature-adjusted-value 50 20) 659.0195889768269 1.000001))
           (is (=ish (temperature-adjusted-value 100 20) 4.47213595499958 1.000001))
           )


(deftest test-temperature-adjusted-probability
  (testing "based on running the python code with sample values"
           (is (=ish (temperature-adjusted-probability 100 0) 0 1.000001))
           (is (=ish (temperature-adjusted-probability 50 0) 0 1.000001))
           (is (=ish (temperature-adjusted-probability 50 20) 19.414213562373096 1.000001))
           (is (=ish (temperature-adjusted-probability 50 0.1) 0.12636038969321073 1.000001))
           ))
