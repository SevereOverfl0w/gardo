(ns gardo.lib
  (:require [clj-time.core :as t]
            [clj-time.coerce :as t-coerce]))

(comment
  ;Ban data looks like so:
  {"some-uuid-object" '({:type ::ban :until :some-date :reason "Fooity bar"}
                        {:type ::kick :at :some-date :reason "Bigger foo & bar"})})

(defn- bans-by-uuid
  [bans uuid]
  (get bans uuid '()))

(defn- active-at?
  [at-time {:keys [until]}]
  (t/before? at-time (t-coerce/from-date until)))

(defn- stateful?
  [{:keys [until]}]
  (if until
    true
    false))

(defn ban-player
  [bans uuid until]
  (let [history (bans-by-uuid bans uuid)]
    (assoc bans uuid
      (cons {:type ::ban :until until} history))))

(defn kick-player
  ([bans uuid reason] (kick-player bans uuid (t-coerce/to-date (t/now)) reason))
  ([bans uuid at reason]
   (let [history (bans-by-uuid bans uuid)]
     (assoc bans uuid
       (cons {:type ::kick :at at :reason reason} history)))))

(defn player-state
  ([bans uuid] (player-state bans uuid (t/now)))
  ([bans uuid since]
   (filter #(and (stateful? %)
                 (active-at? since %))
           (bans-by-uuid bans uuid))))
