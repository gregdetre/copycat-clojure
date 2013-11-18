(in-ns 'copycat-clojure.core)

(defn blah [a b] (+ a b))

;; (defn update-temperature [ws rule & rule-weakness]
;;   (let [rule (:rule ws)
;;         clamp-temperature (:clamp-temperature ws)
;;         total-unhappiness (:total-strength ws)]
;;     ;; inverted the if-logic
;;     (if clamp-temperature
;;       [:temperature (weighted-average [[total-unhappiness 8]
;;                                        [rule-weakness .2]])]
;;       [:rule-weakness (if rule
;;                         (fake-reciprocal (:total-strength rule))
;;                         100)])))


;; (defun get-answer-temperature-threshold-distribution (& bond-density)
;;   (if (and (= (*initial-string* :length) 1)
;;            (= (*target-string* :length) 1))
;;     (def bond-density 1)
;;     (def bond-density 
;;          (/ (length (append (send *initial-string* :bond-list) 
;;                             (send *target-string* :bond-list)))
;;             (+ (1- (send *initial-string* :length))
;;                (1- (send *target-string* :length))))))
  
;;   (cond ((>= bond-density .8) 
;; 	 %very-low-answer-temperature-threshold-distribution%)
;;         ((>= bond-density .6) 
;; 	 %low-answer-temperature-threshold-distribution%)
;;         ((>= bond-density .4) 
;; 	 %medium-answer-temperature-threshold-distribution%)
;;         ((>= bond-density .2) 
;; 	 %high-answer-temperature-threshold-distribution%)
;;         (t %very-high-answer-temperature-threshold-distribution%)))

;; ;---------------------------------------------

;; (defun get-temperature-adjusted-probability (prob &aux low-prob-factor
;; 						       result)
;; ; This function is a filter:  it inputs a value (from 0 to 100) and returns
;; ; a probability (from 0 - 1) based on that value and the temperature.  When
;; ; the temperature is 0, the result is (/ value 100), but at higher 
;; ; temperatures, values below 50 get raised and values above 50 get lowered
;; ; as a function of temperature.
;; ; I think this whole formula could probably be simplified.

;;   (setq result
;; 	(cond ((= prob 0) 0)
;; 	      ((<= prob .5)
;;                (setq low-prob-factor (max 1 (truncate (abs (log prob 10)))))
;;                (min (+ prob 
;; 		       (* (/ (- 10 (sqrt (fake-reciprocal *temperature*))) 
;; 			     100) 
;; 			  (- (expt 10 (- (1- low-prob-factor))) prob)))
;; 		    .5))
		     
;;    	      ((= prob .5) .5)
;; 	      ((> prob .5)
;;                (max (- 1
;; 		        (+ (- 1 prob) 
;; 		           (* (/ (- 10 (sqrt (fake-reciprocal *temperature*))) 
;; 			         100)
;; 			      (- 1 (- 1 prob)))))
;; 	            .5))))
;;   result)
		     
;; ;---------------------------------------------

;; (defun test-get-temperature-adjusted-probability (prob)
;; (with-open-file (ostream "testfile" :direction :output 
;; 	 	                  :if-does-not-exist :create
;; 				  :if-exists :append) 
;; 	(format ostream "prob: ~a~&" prob)
;;   (loop for temp in '(0 10 20 30 40 50 60 70 80 90 100) do
;;         (setq *temperature* temp)
;;         (format ostream "Temperature: ~a; probability ~a~&"
;; 		temp (float (get-temperature-adjusted-probability prob))))
;;   (format ostream "~%")))

;; ;---------------------------------------------

;; (defun get-temperature-adjusted-value-list (value-list &aux exponent)  
;; ; Returns a list with values that are exponential functions of the original
;; ; values, with the exponent being a function of the temperature.  The higher
;; ; the temperature, the bigger the difference between unequal values.
;;   (setq exponent (+ (/ (fake-reciprocal *temperature*) 30) .5))
;;   (loop for value in value-list collect (round (expt value exponent))))

;; ;---------------------------------------------

;; (defun test-get-tempterature-adjusted-value-list (value-list)
;;   (loop for temp in '(0 10 20 30 40 50 60 70 80 90 100) do
;;         (setq *temperature* temp)
;;         (format t "Temperature: ~a; adjusted-value-list: ~a~&"
;; 		temp (get-temperature-adjusted-value-list value-list))))

;; ;---------------------------------------------

;; (defun get-post-codelet-probability (structure-category &aux probability)
;; ; This function gives the program a simple form of self-watching.  For a given
;; ; structure-category (e.g., description, or bond), it returns a probability
;; ; to use in deciding whether or not codelets looking for this type of 
;; ; structure should be posted.  

;;   (cond ((= structure-category 'description)
;; 	 (setq probability (/ (sqr *temperature*) 100)))
	
;; 	((= structure-category 'bond) 
;;          (setq probability (send *workspace* :intra-string-unhappiness)))

;;         ((= structure-category 'group) 
;;          (setq probability (send *workspace* :intra-string-unhappiness)))

;;        ((= structure-category 'replacement)
;; 	(setq probability
;; 	      (if (send *workspace* :unreplaced-objects) then 100 else 0)))

;;        ((= structure-category 'correspondence)
;;         (setq probability (send *workspace* :inter-string-unhappiness)))

;;        ((= structure-category 'rule)
;;         (setq probability 
;; 	      (if (null *rule*) 
;; 	       then 100 else (send *rule* :total-weakness))))

;; 	((= structure-category 'translated-rule)
;;          (setq probability (if *rule* then 100 else 0))))

;;   (/ probability 100))

;; ;---------------------------------------------

;; (defun get-num-of-codelets-to-post (structure-category &aux number)
;; ; This function gives the program a simple form of self-watching.  For a given
;; ; structure-category (e.g., description, or bond), it returns the number
;; ; of codelets looking for this type of structure that should be posted.  

;;   (cond ((= structure-category 'description)
;; 	 (setq number 1))
	
;; 	((= structure-category 'bond) 
;;          (setq number (case (send *workspace* :rough-num-of-unrelated-objects)
;; 			    (few 1) (medium 2) (many 3))))

;;         ((= structure-category 'group) 
;;          (setq number
;; 	       (if (null (send *workspace* :bond-list))
;;                 then 0
;; 	        else (case (send *workspace* 
;; 			         :rough-num-of-ungrouped-objects) 
;; 			         (few 1) (medium 2) (many 3)))))

;;        ((= structure-category 'replacement)
;;         (setq number 
;; 	      (if *rule*
;; 	       then 0
;;                else (case (send *workspace* :rough-num-of-unreplaced-objects)
;;                           (few 1) (medium 2) (many 3)))))


;;        ((= structure-category 'correspondence)
;;          (setq number 
;; 	       (case (send *workspace* :rough-num-of-uncorresponding-objects) 
;; 		     (few 1) (medium 3) (many 3))))

;;        ((= structure-category 'rule)
;; 	(setq number 2))


;;        ((= structure-category 'translated-rule)
;;         (setq number (if (null *rule*) 0 1))))

;;   number)

;; ;---------------------------------------------

