(in-ns copycat-clojure.core)

(defn breaker (& fizzle-probability structure structure-list
                 break-probability quit)
  (let [fizzle-probability (/ (fake-reciprocal *temperature*) 100)]
    (when-not (= (flip-coin fizzle-probability) 'heads)
      (when-let [structure (random-nth (ws :structure-list))]
          (let [structure-list (if (and (typep structure :bond) (structure :group))
                                 (list structure (structure :group))
                                 (list structure))]
            (letfn [(can-be-broken [s]
                                   (= (flip-coin (get-temperature-adjusted-probability 
                                                  (/ (s :total-weakness) 100)))
                                      :tails))]
              (when (all? (map can-be-broken structure-list))
                (map break structure-list))))))))
