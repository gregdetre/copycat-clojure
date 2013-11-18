(in-ns copycat-clojure.core)



(re-seq #"^(\bbye\b)" ":hello bye :cruel world")

(re-seq #"^.*\b(.*)\b.*$" "hello world")

(re-find #"(defun \w*)|(:\w* ?\w*)" "defun my-function")

(re-groups (re-matcher #"(defun \w*)|(:\w* ?\w*)" "defun my-function"))


(re-seq #"\d+" "abc12345def")


