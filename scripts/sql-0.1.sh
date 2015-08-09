cd "$RUNELIGHT_HOME/sql/0.1"

mysql -uroot -pmysql < 0.1-DDL.sql
mysql -uroot -pmysql < 0.1-DML.sql