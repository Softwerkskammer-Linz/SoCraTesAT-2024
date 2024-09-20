# Running LLMs on your hardware with Ollama

Talk at SoCraTes Austria 2024

## What is Ollama

Ollama is a platform that lets you run large language models (like GPT) locallyon your device, offering more privacy, lower latency, and offline access. It simplifies model management, improves performance, and allows customization without relying on cloud services.

https://ollama.com

## Installation on Ubuntu

On a fresh instance of Ubuntu (e.g. on https://digitalocean.com)

### Update system

```
apt-get -y update
apt-get -y upgrade
```

### Install Firewall
```
ufw status
ufw default deny incoming
ufw default allow outgoing
ufw allow ssh
ufw --force enable
```
### Install Ollama

Follow steps from https://ollama.com/download/linux

**Note**: validate downloaded shell script

```
curl -fsSL https://ollama.com/install.sh | sh
```
```
>>> Installing ollama to /usr/local
>>> Downloading Linux amd64 bundle
######################################################################## 100.0%
>>> Creating ollama user...
>>> Adding ollama user to render group...
>>> Adding ollama user to video group...
>>> Adding current user to ollama group...
>>> Creating ollama systemd service...
>>> Enabling and starting ollama service...
Created symlink /etc/systemd/system/default.target.wants/ollama.service → /etc/systemd/system/ollama.service.
>>> The Ollama API is now available at 127.0.0.1:11434.
>>> Install complete. Run "ollama" from the command line.
WARNING: No NVIDIA/AMD GPU detected. Ollama will run in CPU-only mode.
```

## Run Model

### Find available models

Find available models on https://ollama.com/library

### Run a model

Execute the selected model from the command line

```
# ollama run llama3.1
pulling manifest
pulling 8eeb52dfb3bb... 100% |-------------------------------------------------| 4.7 GB
pulling 948af2743fc7... 100% |-------------------------------------------------| 1.5 KB
pulling 0ba8f0e314b4... 100% |-------------------------------------------------|  12 KB
pulling 56bb8bd477a5... 100% |-------------------------------------------------|  96 B
pulling 1a4c3c319823... 100% |-------------------------------------------------|  485 B
verifying sha256 digest
writing manifest
success
```

Chat with the LLM

```
>>> What is SoCraTes?
SoCraTes (Self-Organizing Communities for Testing and Self-Assessment in Software Engineering) is a non-profit organization that aims to promote
testing and self-assessment practices within the software development community.

The core idea behind SoCraTes is to create a space where developers, testers, and other stakeholders can come together to share knowledge,
experiences, and best practices related to testing and quality assurance. The community encourages a collaborative approach to software development,
emphasizing the importance of testing as an integral part of the development process.
```
