(ns copycat-clojure.util
  )


(defn sum [args]
  (apply + args))


(defn run-often [n f & args]
  "For testing non-determinstic functions, runs (apply F
  ARGS) N times, so that you can build up statistics, e.g.
      (run-often select-list-position 3 [1 2 3])."
  (map (fn [_] (apply f args)) (range n)))


(defn pct [num denom] (* (/ num denom) 100.0))


(defn proportion [num denom] (float (/ num denom)))


(defn rsort-by [& args]
  (reverse (apply sort-by args)))


(defn proportions-vec [v]
  "Returns vector of pairs [[val_n proportion_n] ...] ordered by proportion,
by running FREQUENCIES on vector V.

e.g.
  (proportions-vec (run-often 10000 select-list-position [0.1 0.1 0.8]))
  => ([2 0.79958] [1 0.10095] [0 0.09947])

See also PROPORTIONS-MAP.
"
  (let [howmany (count v)]
    (->> v
         frequencies
         (map (fn [[val count]] [val (proportion count howmany)]))
         (rsort-by second))))


(defn proportions-map [v]
  "Like PROPORTIONS_VEC but returns a hash-map {:val1
  proportion1 :val2 proportion ...}"
  (->> (map (fn [[k v]] [(-> k str keyword) v]) (proportions-vec v))
       flatten
       (apply hash-map)))


(defn =ish [x y & [tol]]
  {:pre [(or (nil? tol) (pos? tol))]}
  "Whether (1/TOL < X/Y TOL), i.e. are X and Y fairly close
to one another. Set TOL to be closer to 1 if you want them
to be really similar. If you're happy for them to be within
an order of magnitude, set TOL to 10."
  ;; special case for input of 0. if both are 0, we're fine,
  ;; otherwise raise DivideByZero
  (if (and (zero? 0) (zero? 0))
    true
    (let [tol (or tol 1.1)
          tol (if (> tol 1) tol (/ 1 tol))
          div (/ x y)
          div (if (> div 1) div (/ 1 div))
          ]
      (< (/ 1 tol) div tol))))


(defn all [lst]
  ;; require LST to be non-nil
  {:pre [(not (nil? lst))]}
  (every? identity lst))


(defn select-list-position [probabilities]
  (when (seq probabilities)
    (let [sum (apply + probabilities)
          stop-value (rand sum)]
      (loop [total 0
             index 0]
        (if (> total stop-value)
          (- index 1)
          (recur (+ total (probabilities index))
                 (inc index)))))))


(defn average [v]
  (/ (apply + v) (count v)))


(defn weighted-average [value-weights]
  {:pre [(all (map (comp pos? second) value-weights))]}
  ;; this is truer to the python version
  ;; (if (some (comp pos? second) value-weights), else 0.0)
  (if (seq value-weights)
    (loop [vw value-weights
           total 0.0
           total-weights 0.0]
      (if (seq vw)
        (let [[value weight] (first vw)]
          (recur (rest vw)
                 (+ total (* value weight))
                 (+ total-weights weight)))
        (/ total total-weights)))
    0.0))


(defn coin-flip
  ([] (coin-flip 0.5))
  ([chance] {:pre [(number? chance)
                   (< 0 chance 1)]}
     (< (rand) chance)))


(defn blur [x]
  "Return a number within (sqrt x) of X, e.g. for x = 100,
uniform distribution between 90-110.

N.B. The java and python implementations are wrong - they
just return +/- sqrt(x)."
  ;; xxx MM added 1 to blur, and rounded
  (let [root (Math/sqrt x)
        rand-root (rand root)]
    ((rand-nth [+ -]) x rand-root)))



(defn fake-reciprocal [n]
  (- 100 n))

