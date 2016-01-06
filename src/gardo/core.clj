 (ns gardo.core
   (:require [bukkure.logging :as log]
             [clojure.java.io :as io]
             [clojure.edn :as edn]
             [gardo.util :as util]
             [gardo.events :as events]
             [gardo.commands :as commands])
   (:import [org.bukkit.command TabExecutor]))

(defonce bans (atom {}))

(defn get-ban-file
  "Creates parent directory if necessary"
  [plugin]
  (.mkdirs (.getDataFolder plugin))
  (io/file (.getDataFolder plugin) "bans.edn"))

(defn on-enable [plugin-instance]
  (log/info "Starting Gardo, your Ban Manager")
  (let [state {:bans bans}]
    (events/register plugin-instance state)
    (commands/register plugin-instance state))
  (let [bans-data (or (edn/read-string (util/slurp-quiet (get-ban-file plugin-instance)))
                      {})]
    (reset! bans bans-data)))

(defn on-disable [plugin]
  (let [ban-file (get-ban-file plugin)]
    (spit ban-file (pr-str @bans))))
