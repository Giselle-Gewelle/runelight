# RuneLight

RSPS website project.



## Local (Development) Setup

1. Download and setup Tomcat + MySQL Server
  - Default port for Tomcat: 8080
  - Default port for MySQL: 3306

2. Run database scripts
  - If MySQL is in your PATH, you may use the scripts/sql.sh script to deploy the database scripts
  - If not, run the following files (IN ORDER):
    1. sql/0.1/0.1-DDL.sql
    2. sql/0.1/0.1-DML.sql
    3. sql/0.2/0.2-DDL.sql
    4. sql/0.2/0.2-DML.sql
  - You may want to delete the test database entries from the database and/or DML files before/after installation

3. Setup the RuneLight properties file
  - Locate **runelight.properties**
    1. Open the file and make any neccessary changes
    2. Copy the file into TOMCAT_HOME/conf/

4. Add the RuneLight app entry into Tomcat's server.xml
  - In TOMCAT_HOME/conf/server.xml, find: 
  ```xml
  <Host name="localhost" appBase="webapps" unpackWARs="true" autoDeploy="true">
  ```
  - Under it, add: 
  ```xml
  <Context path="runelight" docBase="RuneLight" debug="0" reloadable="true" sessionCookieDomain=".your.domain" sessionCookiePath="/" />
  ```
  - Replace `your.domain` with your development domain (ie `runelight.giselle` or `runelight.local`)

5. Add the JDBC resource to Tomcat's context.xml
  - In TOMCAT_HOME/conf/context.xml, add: 
  ```xml
  <Resource name="jdbc/runelight" auth="Container"
		  type="javax.sql.DataSource" 
		  username="YOUR_MYSQL_USERNAME" password="YOUR_MYSQL_PASSWORD"
		  driverClassName="com.mysql.jdbc.Driver"
		  url="jdbc:mysql://127.0.0.1:3306/runelight" 
		  maxActive="15" maxIdle="3" />
  ```
  -- Replace `YOUR_MYSQL_USERNAME` and `YOUR_MYSQL_PASSWORD` with their respective values.

6. (IF WINDOWS) Add the subdomain entries into your local Windows hosts file, replacing `your.domain` with your development domain (ie. `runelight.giselle` or `runelight.local`)
  - 127.0.0.1             your.domain
  - 127.0.0.1             www.your.domain
  - 127.0.0.1             create.your.domain
  - 127.0.0.1             news.your.domain
  - 127.0.0.1             forum.your.domain
  - 127.0.0.1             password-history.your.domain
  - 127.0.0.1             password.your.domain
  - 127.0.0.1             recovery-questions.your.domain
  - 127.0.0.1             staff.your.domain
  - 127.0.0.1             ticketing.your.domain

7. Add the following environmental variables
  - JAVA_HOME=the/path/to/your/java/installation/
  - RUNELIGHT_HOME=the/path/to/your/RuneLight/repo/
  - TOMCAT_HOME=the/path/to/your/tomcat/root/

8. Restart all CMD windows.

9. Run RUNELIGHT_HOME/scripts/deploy.sh

10. ...

11. Profit???
