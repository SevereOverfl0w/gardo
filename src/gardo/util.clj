(ns gardo.util
  (:require [bukkure.bukkit :as bk])
  (:import [java.io FileNotFoundException]))

(defn slurp-quiet
  [& args]
  (try
    (apply slurp args)
    (catch FileNotFoundException e
      nil)))

(defn get-prev-player
  [player-name]
  (let [player (.getOfflinePlayer (bk/server) player-name)]
    (if (.hasPlayedBefore player)
        player
        nil)))
