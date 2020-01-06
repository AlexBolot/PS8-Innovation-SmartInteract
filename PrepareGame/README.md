# Prepare Game
### Smart Interact

---

[![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=PrepareGame-c)](http://51.83.69.199:8080/job/PrepareGame-c/)

Module Prupose
===

This module is used to create the reply to a player's message.

Exposed Interface : GamePreparer
===

```
Creates a new Character with a given [pseudo]
-----------------------------
void createCharacter (pseudo)
```
```
Adds a knowledge to the Character of given [ID]
-------------------------------------------------------------
void addKnowledge (ID, keywords, contents, specificKnowledge)
```
```
Sets a difficulty level to the Character of given [ID]
-----------------------------------
void setDifficulty (ID, difficulty)
```
```
Saves changes to the given [character], by replacing its data.
The previous instance is found using Character.ID
--------------------------------
void updateCharacter (character)
```
```
Deletes the given [character] from the JSON storage
--------------------------------
void deleteCharacter (character)
```