(ns copycat-clojure.formulas
  )

(declare weighted-average)
(declare fake-reciprocal)


(defn update-temperature [ws rule & rule-weakness]
  (let [rule (:rule ws)
        clamp-temperature (:clamp-temperature ws)
        total-unhappiness (:total-strength ws)]
    ;; inverted the if-logic
    (if clamp-temperature
      [:temperature (weighted-average [[total-unhappiness 0.8]
                                       [rule-weakness 0.2]])]
      [:rule-weakness (if rule
                        (fake-reciprocal (:total-strength rule))
                        100)])))

