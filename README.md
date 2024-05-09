```yaml
name: CozyGamesAPI
description: The API used to interface with the cozy game system.
author: Smuddgge and Contributors
status: In Development
```

# Brief

```
Ever wanted to have your very own mini-games Minecraft network?
This API is here to make it easy.

Drag and drop the API jar into a server, and boom!
Players will be able to join any game on the network.

Drag and drop a mini-game jar into a server, and boom!
All servers will now let players play that mini-game.
```

# How it works
```yaml
required_argument: []
optional_argument: <>
```

`/play` Shows games you can **join the queue** of and an option to **create a party**.

### Queues
- You can only ever be in **one queue/game** at a time.
- If you are the party leader this will also add the **party members to the queue**.

`/queue` Shows all the games you can queue for. If you have queued it will show the progress and a option to leave.\
`/queue [game]` To join a queue for a game.\
`/queue leave` Will remove you from a queue you have joined.

### Parties
- You can only ever be in **one party** at a time.
- Groups of players are invited or you can also set the party to public.
- This will be similar to game rooms but more flexible.

`/party` Will open the party menu, where you can **create a party, join a party or see your party**.\
`/party list` List the public parties and parties you have been invited to.\
`/party create` Will let you create a party.\
`/party invite <player_name>` Used to invite a player to your party.\
`/party accept <owner_name>` Used to accept a party invite.\
`/party join [public/private] [game]` Used to ether join a public game queue or create a private game for the party.

### General Workings
```
Once a game is triggered a new world will be created, 
the map will be generated and the players will be 
teleported to the spawn point.
```
```
In the future, there may be an option to use ingot 
which is a server hosting provider.

Ingot could be used to create a new server when 
a game is triggered.
```

# Developers

**Class Breakdown**

```yaml
CozyGames: The base api.
CozyGamesProvider: A way of obtaining the api.

CozyGameAPIPlugin: A api plugin.
CozyGamePlugin: A mini-game plugin.

Map: A mini-game map using positions. (A location without a specified world)
LocalMap: Simplified methods for mini-game plugins.
GlobalMap: A map that could be on a different server.

Arena: A map within a world using locations.
LocalArena: Simplified methods for mini-game plugins.
LocalBukkitArena: Simplified local arena for bukkit mini-game plugins.
GlobalArena: A arena that could be on a different server.

Session: A running game in an arena. (Arena with group instance)
SessionComponent: A process that can be running in a session. Providing a clean way of designing a mini-game.
```

**State Diagram**

<img src=".images/state_diagram.png">
