(ns gardo.events
  (:require [bukkure.events :as ev]
            [gardo.lib :as gardo])
  (:import [org.bukkit.event.player PlayerLoginEvent$Result]))

(defn player-login-event
  [bans ev]
  (let [state (gardo/player-state @bans (.. ev getPlayer getUniqueId))]
    (when (seq state)
      (.disallow ev (PlayerLoginEvent$Result/KICK_BANNED)
                    (or (-> state first :reason) "You're banned")))
    true))

(defn player-chat-event
  [bans ev])

(defn events [bans]
  [(ev/event "player.player-login" (partial player-login-event bans))
   (ev/event "player.player-chat" (partial player-chat-event bans))])

(defn register
  [plugin {:keys [bans]}]
  (ev/register-eventlist plugin (events bans)))
