(ns copycat-clojure.constants
  )

;; xxx removed all the graphics constants

(def %verbose% nil)
(def %slightly-verbose% nil)


;---------------------------------------------
; CODELET TYPES

(def %codelet-types% 
     [:bottom-up-description-scout :top-down-description-scout
      :description-strength-tester :description-builder
      :bottom-up-bond-scout :top-down-bond-scout--category
      :top-down-bond-scout--direction
      :bond-strength-tester :bond-builder
      :top-down-group-scout--category :top-down-group-scout--direction
      :group-scout--whole-string :group-strength-tester :group-builder
      :bottom-up-correspondence-scout
      :important-object-correspondence-scout
      :correspondence-strength-tester
      :correspondence-builder
      :rule-scout :rule-strength-tester :rule-builder :rule-translator
      :replacement-finder :breaker
      ])

;; xxx graphics only??? (def %codelet-short-names% ...

(def %built% 3)  ; The number assigned to a structure to indicate that it has been built.


;---------------------------------------------
; CODERACK

(def %max-coderack-size% 100)  ; The maximum number of codelets on the coderack.

(def %num-of-urgency-bins% 7) ; The number of different urgency bins.

;; The URGENCY-VALUE-ARRAY is a table containing the value
;; of an urgency bin as a function of temperature.
;; xxx
;; (def %urgency-value-array% 
;;      (make-array (list %num-of-urgency-bins% 101) :initial-element nil))

;;                                         ; Set up URGENCY-VALUE-ARRAY.
;; (loop for bin-number from 0 to (1- %num-of-urgency-bins%) do
;;       (loop for temperature from 0 to 100 do
;; 	      (aset %urgency-value-array% bin-number temperature 
;; 		    (round (expt (1+ bin-number) 
;;                                  (/ (+ (fake-reciprocal temperature) 10)
;;                                     15.0))))))

;---------------------------------------------
; SLIPNET
  
(def %max-activation% 100)

(def %workspace-activation% 100)
  
;; Threshold activation level for a node to have a chance to
;; go up to full activation.
(def %full-activation-threshold% 50)

;; Number of codelets run before a slipnet update.
(def %time-step-length% 15) 

;; Number of slipnet cycles for initially-clamped slipnodes to be clamped.
(def %initial-slipnode-clamp-time% 50) 
                  
;---------------------------------------------
; PROBABILITY DISTRIBUTIONS

;; xxx
(def %very-low-answer-temperature-threshold-distribution%)
(def %low-answer-temperature-threshold-distribution%)
(def %medium-answer-temperature-threshold-distribution%)
(def %high-answer-temperature-threshold-distribution%)

