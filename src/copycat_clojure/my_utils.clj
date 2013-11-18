(ns copycat-clojure.my-utils
  )

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

