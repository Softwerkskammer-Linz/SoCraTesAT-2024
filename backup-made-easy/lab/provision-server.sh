#!/bin/bash
set -euo pipefail

sudo -s << EOF
dnf makecache
dnf install -y epel-release
dnf install -y borgbackup vim /usr/sbin/semanage
useradd borgbackup
semanage login --add -s user_u borgbackup

mkdir -p /srv/backup
chown -R borgbackup:borgbackup /srv/backup
chmod u=rwX,og= /srv/backup
semanage fcontext -a -t httpd_user_content_t "/srv/([^/]*/)?backup"
restorecon -Rv /srv/backup/

mkdir ~borgbackup/.ssh
chmod 0700 ~borgbackup/.ssh
touch ~borgbackup/.ssh/authorized_keys
chmod 0600 ~borgbackup/.ssh/authorized_keys
chown -R borgbackup:borgbackup ~borgbackup
EOF
