(ns gardo.commands
  (:require [bukkure.commands :as cmd]
            [bukkure.bukkit :as bk]
            [bukkure.player :as p]
            [bukkure.logging :as log]
            [gardo.util :as util]
            [gardo.lib :as gardo]
            [clj-time.coerce :as t-coerce]
            [wordy-date.core :refer [parse] :rename {parse date-parse}]
            ; Deps of wordy-date that I need to reference:
            [instaparse.core :as insta]))

(defn ban-player
  [bans sender _ _ [player & until]]
  (let [player (util/get-prev-player player)
        until (apply str (interpose " " until))
        parsed-date (date-parse until)]
    (cond
      (not player) (p/send-msg sender "No such player found.")
      (insta/failure? parsed-date) (p/send-msg sender "Couldn't parse that date.")
      :else
      (do
        (when (.isOnline player)
          (p/send-msg player "You are being banned until %s" parsed-date))
        (p/send-msg sender "Banning %s until %s" (.getName player) parsed-date)
        (swap! bans gardo/ban-player (.getUniqueId player) (t-coerce/to-date parsed-date))))
    true))

(defn lookup-player
  [bans sender _ _ [player]]
  (let [player (util/get-prev-player player)
        punishments (gardo/player-state @bans (.getUniqueId player))]
    (if (seq punishments)
      (do
        (p/send-msg sender "Active punishments against %s:" (.getName player))
        (doseq [{:keys [type until]} punishments]
          (p/send-msg sender "Player is %s until %s" type until)))
      (p/send-msg sender "No punishments active for %s" (.getName player))))
  true)


(defn register
  [plugin {:keys [bans]}]
  (cmd/register-command plugin "ban" (partial ban-player bans) :player :str)
  (cmd/register-command plugin "lookup" (partial lookup-player bans) :player))
