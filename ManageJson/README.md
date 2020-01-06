# Manage Json
### Smart Interact

---

[![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=ManageJson-c)](http://51.83.69.199:8080/job/ManageJson-c/)

Module Prupose
===

This module has the responsibility to access our JSON storage (read, write).

It also hosts all of the model classes.

This is why most other modules depends on this one : they need access to storage and model, through this module. 

Exposed Interface : Archiver
===

```
Saves the given [dialogue] in a file using a JSON format
--------------------
void save (dialogue)
```
```
Saves the given [character] in a file using a JSON format
---------------------
void save (character)
```
```
Finds a Dialogue instance from the JSON storage, using the given [ID]
------------------------------------
Optional<Dialogue> findDialogue (ID)
```
```
Finds a Character instance from the JSON storage, using its pseudonym
------------------------------------------
Optional<Character> findCharacter (pseudo)
```
```
Get all the list of all characters
---------------------------------
List<Character> getAllCharacter()
```
```
Removes the given [character] from the JSON storage
-----------------------
void remove (character)
```