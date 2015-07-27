cd "$RS_HOME"

mvn clean install

cd target
mv *.war "$RS_HOME"

cd "$RS_HOME"

cp *.war "$TOMCAT_HOME/webapps"