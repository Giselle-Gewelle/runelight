cd "$RUNELIGHT_HOME/sql/1.0.0"

mysql -uroot -pmysql < 1.0.0-DDL.sql
mysql -uroot -pmysql < 1.0.0-DML.sql