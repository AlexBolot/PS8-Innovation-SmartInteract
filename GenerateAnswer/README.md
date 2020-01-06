# Generate Answer
### Smart Interact

---

[![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=GenerateAnswer-c)](http://51.83.69.199:8080/job/GenerateAnswer-c/)

Module Prupose
===

This module is used to create the reply to a player's message.

Exposed Interface : AnswerGenerator
===

```
Process a Message answer corresponding to the given [message] in a given [dialogue]
------------------------------------------
Message generateAnswer (dialogue, message)
    throws CannotMatchKeywordsException
```