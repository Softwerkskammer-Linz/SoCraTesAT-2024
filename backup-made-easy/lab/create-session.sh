#!/bin/bash
set -euo pipefail

session=borgmatic-lab
tmux new-session -s "${session}" -d -n client vagrant ssh client
tmux new-window -d -t "${session}" -n server vagrant ssh server
tmux attach-session -t "${session}"
