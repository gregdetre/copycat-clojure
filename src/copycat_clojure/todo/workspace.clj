(in-ns copycat-clojure.core)

(defrecord Workspace [])

(defmulti letter-list class)
(defmethod letter-list Workspace [ws]
  [(-> ws :initial-string letter-list)
   (-> ws :target-string letter-list)])

(defmulti structure-list class)
(defmethod structure-list Workspace [ws]
  (concat (:bond-list ws)
          (:group-list ws)
          (:correspondence-list ws)
          (or (:rule ws) [])))



(defn abs [x] (if (pos? x) x (- x)))

(defn flip-coin [] (rand-nth [-1 1]))
 
(defn trial
  ([] (trial 100))
  ([thresh] (trial thresh 0 0))
  ([thresh x-over-y counter]
     (if (< (abs x-over-y) thresh)
       (recur thresh (+ x-over-y (flip-coin)) (inc counter))
       counter)))

(trial)

(let [n 10]
  (/
   (apply +
          (for [_ (range n)] (trial)))
   n))

repeatedly 10) (range 10))

