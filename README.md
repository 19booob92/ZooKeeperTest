ZooKeeperTest
=============

Zoo Keeper algorithm test

Na Linuxie wystarczy pobrac ZooKeeper Client ZooKeeper Server, odpala się go przez skrypt "./zkServer.sh start",
teraz kiedy już działa, odpalacie moją apkę, pewnie się wysypie bo tomcat jest już odpalany na 8080 lokalnie, bo wstaje razem z Linuxem, dlatego trzeba go zabić ( ps aux | grep tomcat ), resztą ( deployowaniem też ) zajmie się Spring Boot.
