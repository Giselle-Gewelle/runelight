cd "$RUNELIGHT_HOME"

mvn clean install

cd target
mv *.war "$RUNELIGHT_HOME"

cd "$RUNELIGHT_HOME"

cp *.war "$TOMCAT_HOME/webapps"