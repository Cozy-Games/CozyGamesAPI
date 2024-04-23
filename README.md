```yaml
name: CozyGamesAPI
description: The API used to interface with the cozy game system.
author: Smuddgge and Contributors
status: In Development
```

**Brief**
```
Ever wanted to have your very own mini-games Minecraft network?
This API is here to make it easy.

Drag and drop the API jar into a server, and boom!
Players will be able to join any game on the network.

Drag and drop the mini-game jar into a server, and boom!
That server will now let players play that mini-game.
```

**Implementation**
```
Once an arena is set up and a player starts a game, 
it will generate a new world and create the arena.
```

# Developers

**Class Breakdown**
```yaml
CozyGames: The base api class.
CozyGamesProvide: A way of obtaining the api class.

CozyGameAPIPlugin: Used in the api plugin's.
CozyGamePlugin: Used in mini-game plugin's.

Map: For each mini-game map. (Relative position)
Arena: A loaded map. (Actual locations)
Session: A running game in an arena.
```

**State Diagram**
<img src="images/state_diagram.png">