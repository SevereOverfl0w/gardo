(ns gardo.events
  (:require [bukkure.events :as ev]))

(defn player-join-event
  [ev])

(defn player-chat-event
  [ev])

(defn events []
  [(ev/event "player.player-join" #'player-join-event)
   (ev/event "player.player-chat" #'player-chat-event)])

(defn register
  [plugin state]
  (ev/register-eventlist plugin (events)))
