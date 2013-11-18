
;; (defn get-require [m k]
;;   "Just like GET, but throws an AssertionError if the key K
;; doesn't exist."
;;   (assert (contains? m k))
;;   (m k))


;; ;; this is what i want to be able to do
;; (update-dict {:a 100}
;;              {:b (+ :a 20)
;;               :c (+ :a 30)
;;               })


;; (defn assocs-in2 [d & args]
;;   ;; (apply -> d (for [[k v] args] (list assoc-in k v))))
;;   (apply (comp (reverse (for [[k v] args] (list assoc-in k v)))
;; (let [args [[:cr] [:c1 :c2]]
;;       ]
;;   ;; (apply (comp (reverse
;;                 (for [k-and-v args] [(first k-and-v) (second k-and-v)]));; (list assoc-in k v)))
;;   ;; ) {}))

;; bleurgh. none of these work because you "can't take value of a macro"
;; (apply -> d (for [[k v] args] (list assoc-in k v)))
;; (apply -> d (map #(apply list assoc-in %) args))
;; (apply -> d (map (fn [[k v]] (list assoc-in k v)) args))
