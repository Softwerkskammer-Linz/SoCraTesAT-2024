#!/bin/bash
set -euo pipefail

sudo -s << EOF
dnf makecache
dnf install -y epel-release
dnf config-manager --enable crb --enable epel
dnf install -y borgmatic vim

echo "$1 server" >> /etc/hosts

# Wait for the server to come up
while ! ping -c1 server &> /dev/null
do
  sleep 5
done

mkdir -p /root/.ssh
ssh-keyscan server >> /root/.ssh/known_hosts

chmod 0700 /root/.ssh
chmod 0600 /root/.ssh/known_hosts
EOF
