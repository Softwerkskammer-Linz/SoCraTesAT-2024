# Backup and Restore a database

Launch the tmux session

```
command # Launch the tmux session
sleep 0.5
command ./create-session.sh
sleep 2
```

Install and run mysql

```
# tab 1 in tmux is the client
key ctrl+b 1
sleep 0.5
command sudo -i
sleep 1
command # Install dependencies and launch db server
sleep 0.5
command dnf install -y mysql-server wget unzip
sleep 60
command systemctl enable --now mysqld
sleep 20
```

Import a sample DB and poke around a little

```
command # Import sample db schema
sleep 0.5
command wget https://www.mysqltutorial.org/wp-content/uploads/2023/10/mysqlsampledatabase.zip
sleep 10
command unzip mysqlsampledatabase.zip
sleep 0.5
command mysql < mysqlsampledatabase.sql
sleep 1
command rm -f mysqlsampledatabase.*
sleep 0.5
command # Show row count, content
sleep 0.5
command echo "select count(*) from payments;" | mysql classicmodels
sleep 0.5
command echo "select * from payments limit 5;" | mysql classicmodels
sleep 0.5
command echo "select count(*) from orderdetails;" | mysql classicmodels
sleep 5
```

Create backup

```
command # Create backup
sleep 0.5
command # Adds Mysql stanza to the backup config
sleep 0.5
command cat >> /etc/borgmatic/config.yaml << EOF
command mysql_databases:
command   - name: classicmodels
command EOF
sleep 0.5
command # Create the backup
command borgmatic -v2
sleep 20
```

Delete stuff

```
command # Delete data
sleep 0.5
command echo "drop table payments;delete from orderdetails where orderNumber<10300;" | mysql classicmodels
sleep 3
command # Verify that the data is really gone
command echo "show tables;" | mysql classicmodels
sleep 0.5
command echo "select count(*) from orderdetails;" | mysql classicmodels
sleep 5
```

Restore the backup and verify that the lost data is back

```
command # Restore backup
sleep 0.5
command borgmatic -v2 restore --data-source classicmodels --archive $(borgmatic rlist 2>&1 | tail -n1 | cut -d ' ' -f1)
sleep 30
command # Verify restoration of backup
sleep 0.5
command echo "select count(*) from payments;" | mysql classicmodels
sleep 0.5
command echo "select * from payments limit 5;" | mysql classicmodels
sleep 0.5
command echo "select count(*) from orderdetails;" | mysql classicmodels
sleep 5
```

[modeline]: # vim: nofoldenable:
