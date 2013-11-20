(ns copycat-clojure.formulas
  (:use [copycat-clojure.util]
        [copycat-clojure.constants]
        ))


(defn update-temperature [ws rule & rule-weakness]
  1/0 ;; untested
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


(defn get-answer-temperature-threshold-distribution [ws]
  ;; untested
  1/0
  (let [bond-density (if (and (= (-> ws :initial-string :length) 1)
                              (= (-> ws :target-string :length) 1))
                       1
                       (/ (count (concat (-> ws :initial-string :bond-list) 
                                         (-> ws :target-string :bond-list)))
                          (+ (- 1 (-> ws :initial-string :length))
                             (- 1 (-> ws :target-string :length)))))
        ]
    (cond ((>= bond-density 0.8)
           %very-low-answer-temperature-threshold-distribution%)
          ((>= bond-density 0.6) 
           %low-answer-temperature-threshold-distribution%)
          ((>= bond-density 0.4) 
           %medium-answer-temperature-threshold-distribution%)
          ((>= bond-density 0.2) 
           %high-answer-temperature-threshold-distribution%)
          :else %very-high-answer-temperature-threshold-distribution%)))


(defn temperature-adjusted-value [temperature x]
  (Math/pow x
            (+ 0.5
               (/ (- 100.0 temperature)
                  30.0))))

  
(defn temperature-adjusted-probability [temperature x]
  "Compared with this modified python code:
    def temperatureAdjustedProbability(temperature, x):
            if not x or x == 0.5 or not temperature:
                    return x
            if x < 0.5:
                    return 1.0 - temperatureAdjustedProbability(temperature, 1.0 - x)
            coldness = 100.0 - temperature
            a = math.sqrt(coldness)
            b = 10.0 - a
            c = b / 100
            d = c * (1.0 - (1.0 - x))  # aka c * x, but we're following the java
            e = (1.0 - x) + d
            f = 1.0 - e
            return max(f, 0.5)
"
  (cond (or (zero? x)
            (= x 0.5)
            (zero? temperature)) x
        (< x 0.5) (- 1.0 (temperature-adjusted-probability temperature (- 1 x)))
        :else (let [coldness (- 100 temperature)
                    a (Math/sqrt coldness)
                    b (- 10 a)
                    c (/ b 100)
                    d (* c (- 1 (- 1 x)))
                    e (+ (- 1 x) d)
                    f (- 1 e)
                    ]
                (max f 0.5))))

