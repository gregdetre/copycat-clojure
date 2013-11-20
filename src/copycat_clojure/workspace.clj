(ns copycat-clojure.workspace
  ;; (:use 
        )

(defrecord Workspace [])


;; (defmulti xxx class)
;; (defmethod xxx Workspace [ws] xxx)


(defmulti proposed-bond-list class)
(defmethod proposed-bond-list Workspace [ws]
  [(-> ws :initial-string :proposed-bond-list)
   (-> ws :target-string :proposed-bond-list)])


(defmulti bond-list class)
(defmethod bond-list Workspace [ws]
  [(-> ws :initial-string :proposed-bond-list)
   (-> ws :target-string :proposed-bond-list)])


(defmulti proposed-group-list class)
(defmethod proposed-group-list Workspace [ws]
  [(-> ws :initial-string :proposed-group-list)
   (-> ws :target-string :proposed-group-list)])


(defmulti group-list class)
(defmethod group-list Workspace [ws]
  [(-> ws :initial-string :group-list)
   (-> ws :target-string :group-list)])


;; xxx correspondence-list (flatten (array-to-list correspondence-array))

;; (defmulti add-replacement class)
;; (defmethod :add-replacement Workspace [ws r]
;;   xxx


(defmulti add-proposed-correspondence class)
(defmethod :add-proposed-correspondence Workspace [ws c]
  ;; xxx assoc-in-append should use assoc-in routing, and
  ;; expect to find a collection at that location, and call
  ;; (conj x) on it
  (assoc-in-append ws
            [:proposed-correspondence-array
             (-> c :obj1 :string-number)
             (-> c :obj2 :string-number)]
            c))


(defmulti object-list class)
(defmethod :object-list Workspace [ws]
  [(-> ws :initial-string :object-list)
   (-> ws :target-string :object-list)])


(defmulti letter-list class)
(defmethod letter-list Workspace [ws]
  (map letter-list (it-strings ws)))
  ;; xxx update the other similar functions
  ;; [(-> ws :initial-string letter-list)
  ;;  (-> ws :target-string letter-list)])


(defmulti structure-list class)
(defmethod structure-list Workspace [ws]
  ;; use concat so that rule will be ignored if empty
  (concat (:bond-list ws)
          (:group-list ws)
          (:correspondence-list ws)
          (or (:rule ws) [])))


(defn it-strings [ws]
  [(:initial-string ws)
   (:target-string ws)])


(defn random-string [ws]
  (rand-nth (it-strings ws)))


(defmulti random-object class)
(defmethod random-object Workspace [ws]
  ;; xxx might need to be wrapped to deal with []
  (rand-nth (:object-list ws)))


(defmulti random-group class)
(defmethod random-group Workspace [ws]
  (rand-nth (:group-list ws)))


(defmulti random-correspondence class)
(defmethod random-correspondence Workspace [ws]
  (rand-nth (:correspondence-list ws)))


(defmulti choose-object class)
(defmethod choose-object Workspace [ws f]
;; xxx could this be:
;; (defn choose [lst] (nth (select-list-position (get-temperature-adjusted-value-list lst))))
;; (defn choose-object [ws f] (choose (map f (ws :object-list))))
  (let [objs (ws :object-list)
        value-list (map f objs)
        ]
    (nth (select-list-position (get-temperature-adjusted-value-list value-list))
         objs)))


(defn workspace-happiness [ws happiness-f]
  (let [sum-of-obj-importance-unhappinesses
        (sum (map (fn [obj] (* (obj :relative-importance)
                               (obj happiness-f)))
                  (ws :object-list)))]
    (min 100 (/ sum-of-obj-importance-unhappinesses 200))))


(defmulti intra-string-unhappiness class)
(defmethod intra-string-unhappiness Workspace [ws]
  (workspace-happiness ws :intra-string-unhappiness))


(defmulti inter-string-unhappiness class)
(defmethod inter-string-unhappiness Workspace [ws]
  (workspace-happiness ws :inter-string-unhappiness))


(defmulti total-string-unhappiness class)
(defmethod total-string-unhappiness Workspace [ws]
  (workspace-happiness ws :total-string-unhappiness))


