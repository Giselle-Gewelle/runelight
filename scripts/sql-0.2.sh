cd "$RUNELIGHT_HOME/sql/0.2"

mysql -uroot -pmysql < 0.2-DDL.sql
mysql -uroot -pmysql < 0.2-DML.sql