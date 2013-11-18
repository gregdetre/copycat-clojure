(in-ns copycat-clojure.core)

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
(defn assocs-in [d & args]
  "Like assoc-in, but variadic, so you can feed in multiple pairs "
  (if args
    (let [keypath (first args)
          ;; allow the keypath to be just a single keyword,
          ;; i.e. first-level assoc
          keypath (if (sequential? keypath) keypath [keypath])
          val (second args)
          moreargs (drop 2 args)]
      (apply assocs-in (assoc-in d keypath val) moreargs))
    d))

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


