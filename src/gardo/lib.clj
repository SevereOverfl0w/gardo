(ns gardo.lib
  (:require [clj-time.core :as t]))

(comment
  ;Ban data looks like so:
  {"some-uuid-object" '({:type ::ban :until :some-date}
                        {:type ::kick :until :some-date})})

(defn- bans-by-uuid
  [bans uuid]
  (get bans uuid '()))

(defn- active-at?
  [at-time {:keys [until]}]
  (t/before? at-time until))

(defn ban-player
  [bans uuid until]
  (let [history (bans-by-uuid bans uuid)]
    (assoc bans uuid
      (cons {:type ::ban :until until} history))))

(defn player-state
  ([bans uuid] (player-state bans uuid (t/now)))
  ([bans uuid since]
   (filter (partial active-at? since)
           (bans-by-uuid bans uuid))))
