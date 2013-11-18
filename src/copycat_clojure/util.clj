(ns copycat-clojure.util
  )


(defn run-often [f n & args]
  "For testing non-determinstic functions, runs (apply F
  ARGS) N times, so that you can build up statistics, e.g.
      (run-often select-list-position 3 [1 2 3])."
  (map (fn [_] (apply f args)) (range n)))


(defn pct [num denom] (* (/ num denom) 100.0))


(defn proportion [num denom] (float (/ num denom)))


(defn rsort-by [& args]
  (reverse (apply sort-by args)))


(defn proportions [v]
  "Returns hashmap {val: proportion}, ordered by proportion,
by running FREQUENCIES on vector V.

e.g.
  (proportions (run-often select-list-position 100000 [0.1 0.1 0.8]))
  => ([2 0.79958] [1 0.10095] [0 0.09947])
"
  (let [howmany (count v)]
    (->> v
         frequencies
         (map (fn [[val count]] [val (proportion count howmany)]))
         (rsort-by second))))


(defn =ish [x y & [tol]]
  "Whether (1/TOL < X/Y TOL), i.e. are X and Y fairly close
to one another. Set TOL to be closer to 1 if you want them
to be really similar. If you're happy for them to be within
an order of magnitude, set TOL to 10."
  (let [tol (or tol 1.1)
        tol (if (> tol 1) tol (/ 1 tol))
        div (/ x y)
        div (if (> div 1) div (/ 1 div))
        ]
    (< (/ 1 tol) div tol)))


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




