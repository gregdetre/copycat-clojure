(ns copycat-clojure.core)

(defn start-ccat [& {:keys [time-step-length verbose]
                     :or {time-step-length 1
                          verbose true
                          }}]
  (run-ccat (make-ccat)))

(defn make-ccat []
  {:quit-program false
   :maxsteps 5
   :nsteps 0
   :cr {;; coderack
        :codelet-count 0}
   :sn { ;; slipnet
        :slipnodes []} ; xxx (initially-clamped-slipnodes)
   })

;; (defn run-ccat [ccat]
;;   (if (< (ccat :nsteps) (ccat :maxsteps))
;;     (do
;;       (println "Step" (ccat :nsteps))
;;       (recur (update-in ccat [:nsteps] inc)))
;;     ccat))

;; xxx - this is the macro i want
;; (upd ccat
;;      :blah (do-blah)
;;      :nodes (do-nodes))

(defn run-ccat [ccat]
  ;; xxx
  (let [everything-changes (if (= (mod codelet-count time-step-length) 0)
                             (update-everything)
                             {})
        node-changes (if (empty? (ccat :cr))
                       (clamp-initially-clamped-slipnodes)
                       {})
        ]
    (-> ccat
        into {blah-changes
              :sn node-changes
              }
        step)))

(defn clamp-initially-clamped-slipnodes []
  (for [node (initially-clamped-slipnodes)]
    (assoc node :set-clamp t)))

(run-ccat {:nsteps 0 :maxsteps 5})

(run-ccat)


(defn update-everything [ccat & new-structure-list unclamp-probability]
  xxx)


(defun step-ccat (&aux codelet)  
  xxx)


(defun deal-with-snag ()
  xxx)


(defun unanswer [ccat]
  (run-ccat (into ccat {:translated-rule nil
                        :answer-string nil
                        :found-answer nil
                        :quit-program nil})))

