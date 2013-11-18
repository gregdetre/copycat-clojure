(in-ns copycat-clojure.core)

(defrecord Copycat [quit-program nsteps maxsteps
                    ws cr sn])
;; (defmulti p class)
;; (defmethod p Copycat [ccat] "hello")
;; (p (init-ccat))

(defn init-ccat []
  (let [ccat (Copycat.
              false 0 3
              {} [] {})
        ccat (assoc ccat :codelet-count 0)
        ]
    ccat))


(defn post-initial-codelets []
  [:c1 :c2 :c3])


(defn sn-set-clamp-if-empty-cr [ccat]
  (when (-> ccat :cr empty?)
    [[:sn :nodes] (for [node (-> ccat :sn :nodes)]
                         (assoc node :set-clamp true))
     :cr (post-initial-codelets)
     ]))

(is (= (:sn (apply assocs-in (init-ccat) (sn-set-clamp-if-empty-cr (init-ccat))))
       {:nodes ()}))


(defn sn-unclamp-initially-clamped [ccat]
  (when (= (:codelet-count ccat)
           (* (:initial-slipnode-clamp-time ccat) (:time-step-length ccat)))
    (let [keypath [:sn :nodes]
          nodes (ccat :initially-clamped-slipnodes)
          newval (map #(assoc (% :set-clamp nil)) nodes)
          ]
      [keypath newval])))


;; xxx
(declare update-strength-values)
(declare update-object-values)
(declare update-relative-importances)
(declare update-intra-string-unhappiness)
(declare update-temperature)
(declare get-bottom-up-codelets)
(declare get-top-down-codelets)
(declare update-slipnet)
(declare post-codelet-list)



;; (defn update-everything [ccat]
;;   (println (:nsteps ccat) ") Updating")
;;   ;; do something
;;   (let [nsteps (-> ccat :nsteps inc)
;;         quit-program (>= nsteps (:maxsteps ccat))
;;         ]
;;     (assoc ccat
;;       :nsteps nsteps
;;       :quit-program quit-program)))


(defn update-everything [ccat]
  (let [ccat (assocs-in ccat :updating-everything true)
        ;; xxx replace with update-in
        ;; ccat (update-in ccat [:ws :structure-list] (partial map update-strength-values))
        ccat (assocs-in ccat [:ws :structure-list]
                        (map update-strength-values (-> ccat :ws :structure-list)))
        ccat (assocs-in ccat [:ws :object-list]
                        (map update-object-values (-> ccat :ws :object-list)))
        ccat (assocs-in ccat
                        [:initial-string] (update-relative-importances (-> ccat :initial-string))
                        [:target-string] (update-relative-importances (-> ccat :target-string)))
        ccat (assocs-in ccat
                        [:initial-string] (update-intra-string-unhappiness (-> ccat :inital-string))
                        [:target-string] (update-intra-string-unhappiness (-> ccat :target-string)))
        ccat (assocs-in ccat (sn-unclamp-initially-clamped ccat))
        ccat (if (> (:codelet-count ccat) 0)
               (assocs-in ccat
                          (update-temperature ccat)
                          (get-bottom-up-codelets ccat)
                          (get-top-down-codelets ccat)
                          (update-slipnet ccat))
               ccat)
        ccat (assocs-in ccat
                        (post-codelet-list ccat)
                        :codelets-to-post nil)
        ccat (assocs-in ccat :updating-everything nil)
        ]
    ccat))


(declare choose)
(declare codelet-run)

(defn step [ccat]
  (let [codelet (choose (:cr ccat))
        xxx (codelet-run ccat codelet)
        ]
    [:codelet-run :xxx
     :codelet-count (-> inc :codelet-count ccat)
     ]))



;; xxx
(defn deal-with-snag [ccat]
  [:snag true])
  
 
(defn main-loop [ccat]
  (let [ccat (update-everything ccat)
        ccat (assocs-in ccat (sn-set-clamp-if-empty-cr ccat))
        ccat (assocs-in ccat (step ccat))
        ; xxx translated-rule
        ]
    (if (:quit-program ccat)
      ccat
      (recur ccat))))
    


(defn run-ccat []
  (let [ccat (init-ccat)]
    (println "Init: " ccat)
    (main-loop ccat)))


(declare main-loop)


;; (defn unanswer [ccat]
;;   (assocs-in ccat
;;              :translated-rule nil
;;              :answer-string nil
;;              :found-answer nil
;;              :quit-program nil)
;;   ;; (display ccat)
;;   (main-loop ccat))


(run-ccat)


