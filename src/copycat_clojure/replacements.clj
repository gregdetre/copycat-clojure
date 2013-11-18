(in-ns copycat-clojure.core)
			     
(defrecord Replacement [obj1 obj2])


(defn replacement-finder (& i-letter m-letter i-letter-category
                            m-letter-category change-relation
                            new-replacement)
  (let [i-letter (*initial-string* :random-letter)]
    (when-not (i-letter :replacement)
      (let [m-letter ((*modified-string* :get-letter)
                      (i-letter :left-string-position))
            i-letter-category (i-letter :get-descriptor plato-letter-category)
            m-letter-category (m-letter :get-descriptor plato-letter-category)]
        (when-not (= i-letter-category m-letter-category)
          (def i-letter (assoc i-letter :set-changed? t))
          (when-let [change-relation (get-label-node i-letter-category m-letter-category)]
            (m-letter :add-extrinsic-description 
                      (make-extrinsic-description 
                       change-relation plato-letter-category i-letter)))

  (setq new-replacement (make-replacement i-letter m-letter))
  (send *workspace* :add-replacement new-replacement)
  (send i-letter :set-replacement new-replacement)
  )))))			
