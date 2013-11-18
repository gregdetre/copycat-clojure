(ns copycat-clojure.my-utils-test
  (:use [clojure.test]
        [copycat-clojure.my-utils]))


(deftest assocs-in-test
  (testing
   "Assocs-in"
   (is (= (assocs-in {} [:cr] [:c1 :c2])
          (assoc-in {} [:cr] [:c1 :c2])))
   (is (= (assocs-in {:sn {}} [:sn :nodes] [:n1 :n2])
          (assoc-in {:sn {}} [:sn :nodes] [:n1 :n2])))
   (is (= (assocs-in {} :cr [:c1 :c2]) ; single-level keypath
          (assoc-in {} [:cr] [:c1 :c2])))
   (is (= (assocs-in {:sn {:nodes [:n1]}}
                     [:sn :nodes] [:n2] [:cr] [:c1 :c2])
          (-> {:sn {:nodes [:n1]}}
              (assoc-in [:sn :nodes] [:n2])
              (assoc-in [:cr] [:c1 :c2]))))
   ))
