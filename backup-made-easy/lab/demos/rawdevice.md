# Backup and Restore a raw device

Launch the tmux session

```
command # Launch the tmux session
sleep 0.5
command ./create-session.sh
sleep 2
```

Create a device to back up

```
# tab 1 in tmux is the client
key ctrl+b 1
sleep 1
command # Create a device to back up
sleep 0.5
command sudo -i
sleep 1
command fallocate -l 100M /srv/foo.img
sleep 1
command mkfs.ext4 /srv/foo.img
sleep 5
command losetup /dev/loop0 /srv/foo.img
sleep 0.5
command mount /dev/loop0 /mnt
sleep 0.5
command echo "This is a test" > /mnt/foo
sleep 0.5
command sync
sleep 0.5
command umount /mnt
sleep 0.5
```

Back up the raw device

```
command # Enable backing up the raw device
command sed -i -e '6a\\ - /dev/loop0' -e '6aread_special: true' /etc/borgmatic/config.yaml
sleep 0.5
command # Back up the raw device
command borgmatic -v2
sleep 20
```

Delete & recreate the device

```
command # Delete & recreate the device
command losetup -d /dev/loop0
sleep 1
command rm -f /srv/foo.img
sleep 1
command sync
sleep 1
command fallocate -l 100M /srv/foo.img
sleep 1
command losetup /dev/loop0 /srv/foo.img
sleep 2
```

Restore

```
command # Restore
sleep 0.5
command borgmatic mount --mount-point /mnt --archive $(borgmatic rlist 2>&1 | tail -n1 | cut -d ' ' -f1)
sleep 20
command dd if=/mnt/dev/loop0 of=/dev/loop0 bs=4M status=progress
sleep 30
command umount /mnt
sleep 1
command mount /dev/loop0 /mnt
sleep 1
command cat /mnt/foo
sleep 5
```

[modeline]: # vim: nofoldenable:
