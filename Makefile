default:
	make plugin
	make copy_plugin
	make start_server

server:
	mkdir server
	wget http://getspigot.org/spigot18/craftbukkit_server.jar -O server/server.jar

start_server:
	cd server && java -jar server.jar

plugin:
	lein uberjar

copy_plugin:
	mkdir -p server/plugins
	cp target/gardo*-standalone.jar server/plugins/gardo.jar
