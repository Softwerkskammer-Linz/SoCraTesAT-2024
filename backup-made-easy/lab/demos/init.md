# Set up the backup server

Launch the tmux session

```
command # Launch the tmux session
sleep 0.5
command ./create-session.sh
sleep 0.5
```

Set up the backup repository on the server

```
# tab 2 in tmux is the server
key ctrl+b 2
sleep 0.5
command # Become root
command sudo -i
command # Set up the backup repository on the server
command REPO_DIR="/srv/backup"
sleep 0.5
command # Create the directory
command mkdir -p "${REPO_DIR}"
sleep 0.5
command # Set permissions
command chown borgbackup.borgbackup "${REPO_DIR}"
sleep 0.5
command chmod -R u=rwX,g=rX,o= "${REPO_DIR}"
sleep 0.5
command # Set SELinux context
command restorecon -Rv "${REPO_DIR}"
sleep 0.5
```

Set up the `.ssh` directory for the `borgbackup` user

```
command # Set up the `.ssh` directory for the `borgbackup` user
command # Create the .ssh directory and authorized_keys file
command mkdir -p ~borgbackup/.ssh
sleep 0.5
command touch ~borgbackup/.ssh/authorized_keys
sleep 0.5
command # Set permissions
command chmod 0600 ~borgbackup/.ssh/authorized_keys
sleep 0.5
command chmod 0700 ~borgbackup/.ssh
sleep 0.5
command chown borgbackup:borgbackup -R ~borgbackup
sleep 0.5
command restorecon -Rv /home
```

Create an SSH key on the client and copy the public key to the server

```
command # Create an SSH key on the client and copy the public key to the server
# tab 1 in tmux is the client
key ctrl+b 1
sleep 0.5
command # Become root
command sudo -i
command # Create an eddy key with appropriate strength without a passphrase
command ssh-keygen -t ed25519 -a 420 -C "Borgmatic $(hostname --fqdn) backup key" -f /etc/borgmatic/ssh -N ''
sleep 0.5
command # Export the SSH key to the vagrant user
command cp /etc/borgmatic/ssh.pub ~vagrant/ssh.pub
command chown vagrant:vagrant ~vagrant/ssh.pub
sleep 0.5
# tab 2 in tmux is the server
key ctrl+b 2
sleep 0.5
command # Back on the server (still root), copy the public key
command scp vagrant@client:ssh.pub ~borgbackup/ssh.pub
sleep 25
key y e s Return
sleep 2
key v a g r a n t Return
sleep 0.5
command # Put it into authorized_keys
command cat > ~borgbackup/.ssh/authorized_keys << EOF
command command="/usr/bin/borg serve --restrict-to-repository ${REPO_DIR}/client",restrict $(cat ~borgbackup/ssh.pub)
command EOF
sleep 0.5
command rm -f ~borgbackup/ssh.pub
sleep 0.5
```

On the client, make borgmatic set up the repository

```
command # Switch back to the client
# tab 1 in tmux is the client
key ctrl+b 1
sleep 0.5
command # Purge the exported key
command rm -f ~vagrant/ssh.pub
sleep 0.5
command # Create borgmatic's config and set up the repository
sleep 0.5
command # Copy the sample config
command cp /vagrant/sample_config.yaml /etc/borgmatic/config.yaml
sleep 0.5
command # Skip the SSH TOFU prompt
command ssh-keyscan server > ~/.ssh/known_hosts
sleep 4
command # Initialize the backup repo
command borgmatic -v2 init -e 'repokey-blake2'
sleep 0.5
```

After successful backup repo creation, perform the backup

```
command # Perform a backup
command borgmatic -v2
sleep 5
command # List the backups
command borgmatic rlist
```

[modeline]: # vim: nofoldenable:
