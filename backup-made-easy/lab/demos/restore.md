# Backup and Restore

Launch the tmux session

```
command # Launch the tmux session
sleep 0.5
command ./create-session.sh
sleep 2
```

Create data, backup and lose it

```
# tab 1 in tmux is the client
key ctrl+b 1
sleep 0.5
command sudo -i
sleep 1
command # Create data
sleep 0.5
command echo "This is a test" > /srv/foo
sleep 0.5
command borgmatic -v2
sleep 5
command rm -rf /srv/*
sleep 0.5
```

Data is gone, restore it

```
command borgmatic rlist
sleep 3
command # Restore selectively with FUSE Mount
sleep 0.5
command borgmatic mount --mount-point /mnt --archive $(borgmatic rlist 2>&1 | tail -n1 | cut -d ' ' -f1)
sleep 5
command ls /mnt
sleep 0.5
command find /mnt | wc -l
sleep 0.5
command rsync -avvz --progress /mnt/srv/ /srv/
sleep 1
command # Restore complete
command cat /srv/foo
sleep 2
command umount /mnt
sleep 0.5
```

Delete it again, restore with `borgmatic extract`

```
command # Delete it again, restore with `borgmatic extract`
command rm -rf /srv/*
sleep 0.5
command borgmatic extract --archive $(borgmatic rlist 2>&1 | tail -n1 | cut -d ' ' -f1) --path /srv --destination / --progress -v2
sleep 5
command cat /srv/foo
sleep 0.5
```

[modeline]: # vim: nofoldenable:
