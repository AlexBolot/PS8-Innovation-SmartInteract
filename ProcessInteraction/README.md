# Process Interaction
### Smart Interact

---

[![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=ProcessInteraction-c)](http://51.83.69.199:8080/job/ProcessInteraction-c/)

Module Prupose
===

The `ProcessInteraction` module is one of the entry points of SmartInteract.

It is used by the Game Developper to submit the player's message and obtain the appropriate reply.

It uses a `CORBA` system to allow multi-language communication.

Exposed Interface : InteractionProcesssor
===

```
Registers a player's message sent to a character, and returns the character's reply
-------------------------------
Replique sendMessage (replique)
```
```
Returns the ID of a dialogue between the player and the given character (using idCharacter)
The dialogue is created if not already existing
----------------------------------
String beginDialogue (idCharacter)
```
```
Fetches the unique ID of a character that matches the given [pseudo]
------------------------------
String getCharacterID (pseudo)
```

Start InteractionProcessor
===

To launch this entry point of our solution, you will also need to start `ORBD`.

orbd on Windows :
--- 
```
start orbd -ORBInitialPort 1050 -ORBInitialHost localhost
```
orbd on Mac or unix:
---
```
orbd -ORBInitialPort 1050 -ORBInitialHost localhost
```

Interaction Processor :
---
```
java fr.polytech.pnsinnov.smartinteract.Main -ORBInitialPort 1050 -ORBInitialHost localhost
```

Generate Stubs
===

To communicate with `ProcessInteraction`, you need to generate Stubs in your video-game's language.
You will need to use the [`.idl`](https://github.com/PNS-PS7-1819/ProcessInteraction-c/blob/develop/src/main/java/InteractionProcessor.idl) file.


Java
---
```
idlj -fall -td <output-path> InteractionProcessor.idl
```
C++
---

install `omniidl` with the following [link](https://pypi.org/project/omniidl/)

```
omniidl -bcxx InteractionProcessor.idl 
```
Python
---

install `omniidl` with the following [link](https://pypi.org/project/omniidl/)

```
omniidl -bpython InteractionProcessor.idl
```