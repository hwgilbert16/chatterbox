# Chatterbox

[![CodeFactor](https://www.codefactor.io/repository/github/hwgilbert16/chatterbox/badge/main)](https://www.codefactor.io/repository/github/hwgilbert16/chatterbox/overview/main)
![GitHub](https://img.shields.io/github/license/hwgilbert16/chatterbox)

Chatterbox is a convient and dead-simple way to unify a Discord channel and a Minecraft chat, allowing players to seamlessly communicate regardless of their presence in the server.

All features are configurable to modify the plugin to exactly your needs.

<img src="https://i.gyazo.com/fc70a73bd37b254eaf55ab0f1cd4f4f5.png" width="50%" />
<img src="https://i.gyazo.com/81164a1b6e92389bc7eba282889cff7f.png" width="50%" />

## Features

- **All settings** fully configurable
- Compatibility with spigot-based servers
- Ability for users in the Discord to send messages to the Minecraft chat, and vice versa
- Able to ping people in the Discord server from the Minecraft chat
- Notification of server status and restriction of channel when the server is offline
- Notification of player leave and joins
- Notification of player deaths

## How it works

Chatterbox comes with two parts, a plugin and a companion Discord bot. The two of them communicate over a websocket connection for mutually exchanging information.

The plugin installs into the server and sends chat messages and player events to be sent to a webhook. It listens for messages from the Discord bot to mirror into the server chat.

The Discord bot relays messages from the Discord to the plugin. It listens for messages from the plugin to mirror into the Discord channel. It also keeps track of the health of the websocket connection, and locks the Discord channel if the connection is found to be lost.

## Installation

The **recommended way to setup the Discord bot is by using our Docker container on a Linux system**, as it is significantly easier. However, it is possible to run the Discord bot outside of a container. Both methods will be outlined below.

### Docker Container

Before starting installation, be sure that Docker is installed on the system that you will be running the Discord bot in. Downloads for Docker can be found below if you do not already havee it installed.

- [Linux](https://docs.docker.com/linux/started/)
- [Windows](https://docs.docker.com/windows/started)
- [MacOS (OS X)](https://docs.docker.com/mac/started/)

Pull the image from Docker Hub. This may take a couple minutes depending on the speed of your internet connection, among other factors.

```
docker pull hwgilbert16/chatterbox
```
