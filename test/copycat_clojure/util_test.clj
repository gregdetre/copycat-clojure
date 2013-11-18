(ns copycat-clojure.util-test
  (:use [clojure.test]
        [copycat-clojure.util]))


(deftest test-=ish
  (testing "default tol"
           (is (=ish 1 1.001))
           (is (=ish 1 0.999))
           (is (not (=ish 1 1.2)))
           (is (not (=ish 1 0.8)))
           )
  (testing "specified tol"
           (is (=ish 1 1.5 2))
           (is (=ish 1 0.6 2))
           (is (not (=ish 1 3 2)))
           (is (not (=ish 1 0.4 2)))
           ))


(deftest test-all
  (testing "basic all"
           (is (all [1 2 3]))
           (is (all []))
           (is (all [1 2 []]))
           )
  (testing "false"
           (is (not (all [false])))
           (is (not (all [1 2 false])))
           (is (not (all [1 2 nil])))
           )
  (testing "can't be nil (following python)"
           (is (thrown? AssertionError (all nil)))
           )
  )


(deftest test-select-list-position
  (testing
   "select-list-position empty"
   (is (= (select-list-position nil)
          nil))
   (is (= (select-list-position [])
          nil)))
   (testing
    "select-list-position proportions"
    (let [desired-propns [0.8 0.1 0.1]
          howmany 100000
          actual-val-propns (proportions
                             (run-often select-list-position howmany desired-propns))
          actual-propns (map second actual-val-propns)
          ]
      (is (all (map (fn [[x y]] (=ish x y 1.2))
                    (partition 2 (interleave desired-propns actual-propns)))))
      ))
   )

     
