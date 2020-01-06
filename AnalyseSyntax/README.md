# Analyse Syntax
### Smart Interact

---

[![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=AnalyseSyntax-c)](http://51.83.69.199:8080/job/AnalyseSyntax-c/)

Module Prupose
===

This module is used by our system to apply Natural Language Processing (NLP) to the player's message.

1. We send a `Message` instance containing the `content` and an empty list of tokens.

2. Using [SpaCy](https://spacy.io/), we extract a list of tokens from the phrase, corresponding to key words, names of places, names of characters, etc.
This is what we use to understand the player's message, and give the appropriate reply

3. Then we return the same `Message`, containing the list of tokens (and the original content).

Exposed Interface : SyntaxAnalyser
===
```
Uses a SpaCy script to analyse the given [message]
---------------------------------------------------
Message analyseMessage (message) throws IOException
```
```
Does nothing for now
------------------
void endDialogue()
```

Start Analyse Syntax
===

> Need more information from @AlexanneM

SpaCy
===

We use Spacy as our language processor, as it is able to understand many languages, very accuratly and fast.

SpaCy is only available in `Python`, which is why we need to run it outside of our `Java` solution.

> Need more information from @AlexanneM

|  SYSTEM  | YEAR | LANGUAGE | ACCURACY | SPEED (WPS) |
| -------- | :--: | :------: | -------: | ----------: |
|`spaCy v2`| 2017 |  Python  |     92.6 |         n/a |
|`spaCy v1`| 2015 |  Python  |     91.8 |      13,963 |
|`ClearNLP`| 2015 |   Java   |     91.7 |      10,271 |
|`CoreNLP` | 2015 |   Java   |     89.6 |       8,602 |
|`MATE`	   | 2015 |   Java   |     92.5 |         550 |
|`Turbo`   | 2015 |    C++   |     92.4 |         349 |

> Source : official benchmark of [Spacy.io](https://spacy.io/usage/facts-figures#benchmarks)
