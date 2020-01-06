# Monitor
### Smart Interact

---

[![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=Monitor-c)](http://51.83.69.199:8080/job/Monitor-c/)

Module Prupose
===

Monitor provides character's data to the DesignerTool application.

> It is expected to provide statistics in the future.

Exposed Interface : Monitor
===

```
Fetches the list of all characters from Archiver
---------------------------------
List<Character> getAllCharacter()
```
```
Fetches a single Character that matches the given [pseudo]
----------------------------
Character getByName (pseudo)
```