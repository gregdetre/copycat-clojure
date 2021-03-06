(ns copycat-clojure.core)

(defprotocol BondProtocol
  (out [x]))

(defrecord Bond [x y]
  BondProtocol
  (out [this]
       (format "%s, %s" x y)))

(def b (Bond. 100 200))


(defn bond-make [from-obj to-obj bond-category bond-facet 
                 from-obj-descriptor to-obj-descriptor]

  (defn deduce-left-and-right-objs [from-obj to-obj]
    (if (< (from-obj :left-string-position) (to-obj :left-string-position))
      {:left-obj from-obj, :right-obj to-obj}
      {:left-obj to-obj, :right-obj from-obj}))

  (let [bond {:from-obj from-obj
              :to-obj to-obj
              :structure-category :bond
              :bond-category bond-category
              :direction-category (if (= bond-category :plato-sameness)
                                    nil
                                    (if (< (from-obj :left-string-position)
                                           (to-obj :left-string-position))
                                      :plato-right
                                      :plato-left))
              :string (from-obj :string)
              :left-string-position (min (from-obj :left-string-position) 
                                         (to-obj :left-string-position))
              :right-string-position  (max (from-obj :right-string-position) 
                                           (to-obj :right-string-position))
              :bond-facet bond-facet
              :from-obj-descriptor from-obj-descriptor
              :to-obj-descriptor to-obj-descriptor
              }
        bond (merge bond
                    (deduce-left-and-right-objs (bond :from-obj) (bond :to-obj))
                    {:letter-span (+ (-> bond :from-obj :letter-span)
                                     (-> bond :to-obj :letter-span))
                     :leftmost-in-string? (= (bond :left-string-position?) 0)
                     :right-string-position? (= (bond :right-string-position)
                                                (- 1 (-> bond :string :length)))
                     :print (format "%s-%s:%s %s %s (%s), level %s"
                                    (-> bond :from-obj :left-string-position)
                                    (-> bond :to-obj :left-string-position)
                                    (-> bond :from-obj :pname)
                                    (-> bond :bond-category)
                                    (-> bond :to-obj :pname) 
                                    (-> bond :direction-category)
                                    "proposal-level???"
                                    )
                     })]
    bond))

;; (defn bond-choose-left-neighbor [bond & possible-left-neighbor left-neighbor-list]
;;   (if (bond :leftmost-in-string?)
;;     nil
;;     (let [left-neighbor-list (for [left-neighbor-obj (-> bond :left-obj :all-left-neighbors)]
;;                                (setq possible-left-neighbor

;; 		             (aref (send string :left-right-bond-array)
;;                                    (send left-neighbor-obj :string-number)
;; 			           (send (send self :left-obj) 
;; 					 :string-number)))
;; 	      when possible-left-neighbor
;; 	      collect possible-left-neighbor))
;;         (select-list-item-by-method left-neighbor-list ':salience)))


(defn bond-members? [bond obj1 obj2]
  "Returns t if the two objects are the objects in this bond."
  (and (or (= (bond :from-obj) obj1) (= (bond :to-obj) obj1)) 
       (or (= (bond :from-obj) obj2) (= (bond :to-obj) obj2))))


(#{1 2 3} 2)

(defn bond-in-group? [bond g]
  "Returns t if the bond is in the group g."
  ;; (g :object-list) returns a set of objects, against
  ;; which we're checking membership
  (and ((g :object-list) (bond :from-obj))
       ((g :object-list) (bond :to-obj)))


(defn bond-make-bottom-up-bond-scout [ws
                                      & from-obj to-obj bond-facet 
                                      from-obj-descriptor to-obj-descriptor 
                                      bond-category)
  ;; see http://inclojurewetrust.blogspot.com/2010/12/when-let-maybe.html on when-nlet
  (when-let [from-obj (choose-object ws :intra-string-salience)]
    (when-let [to-obj (choose-neighbor from-obj)]
      (when-let [bond-facet (choose-bond-facet from-obj to-obj)]
        (when-let [from-obj-descriptor (get-descriptor from-obj bond-facet)]
          (when-let [to-obj-descriptor (get-descriptor to-obj bond-facet)]
            (when-let [bond-category (get-bond-category from-obj-descriptor to-obj-descriptor)]
    (propose-bond from-obj to-obj bond-category bond-facet 
                  from-obj-descriptor to-obj-descriptor))))))))


;; e.g.
(def bond1 (bond-make
            {:left-string-position 0 :right-string-position 1 :letter-span 1 :pname "from-pname" :string {:length 1}}
            {:left-string-position 1 :right-string-position 2 :letter-span 1 :pname "to-pname" :string {:length 1}}
            :my-cat
            :my-facet
            :my-f-desc
            :my-t-desc))

(bond1 :print)

(defn blah []
  {:a 100
   :fn1 #(+ % 37)
   })

(def x (blah))

(-> x :a)
(x :a)

((x :fn1) 10)






