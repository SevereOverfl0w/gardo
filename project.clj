(defproject io.dominic/gardo "0.1.0-SNAPSHOT"
  :description "A plugin to manage punishments"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [bukkure "0.5.0-SNAPSHOT"]
                 [philjackson/wordy-date "0.1.2"]
                 [clj-time "0.11.0"]]
  :repositories [["spigot-repo" "https://hub.spigotmc.org/nexus/content/groups/public/"]]
  :profiles {:provided
             {:dependencies [[org.bukkit/bukkit "1.8.8-R0.1-SNAPSHOT"]]}}
  :java-source-paths ["javasrc"])
