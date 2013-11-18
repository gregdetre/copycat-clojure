(in-ns copycat-clojure.core)

(defrecord workspace-structure 
    [string ; The string the structure is in.
     structure-category ; E.g., 'bond or 'group.
     group  ; T if the structure (e.g., a bond) is inside a group.
     internal-strength
     external-strength
     total-strength
     proposal-level])

;; e.g. (workspace-structure. "abc" 'bond 'my-group 1 2 3 'proposal-level?)

