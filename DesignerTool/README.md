# Designer Tool
### Smart Interact

---

![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=DesignerTool-c)

Module Purpose
===

This part of our solution is the graphical interface made for the Game Designers to work with SmartInteract.

Currently there are three main features available :
- [Adding new characters](#Adding-new-characters)
- [Accessing and updating existing characters](#Accessing-existing-characters)
- [Testing a character's behavior](#Testing-the-behavior)

Adding new characters
---
You can create and add characters to your game by giving a `name`, a `difficulty` and a set of `knowledge items`

- `name` : a `string` value for the character's name.
Every character shall have a different name.

- `difficulty`: a `double`value representing how complex it is to obtain informations from the character
    - The higher the value, the more difficult it is
    - Usually on a range between `1.0` and `5.0`

- `knowledge`: composed of set of `string` keywords and one or many `string` contents.
    - These are used as possible replies for the character during a conversation with the player
    - keywords must be comma-separated
    - the content can be just a simple phrase, or a list of phrases (one will be chosen randomly to create the reply), you need to use dashes `-` to separate phrases
    > Example : this is a first possible answer - this is another possible answer

Accessing existing characters
---
You can visualize the data of a given character by selecting its name in the list of characters.

This allows you to change all parameters (`name`, `difficulty` and `knowledge items`).

Testing the behavior
---

We provide a text console that allows you to chat with the characters, test their replies to your input, and see modifications on-the-go.

Every changes made using the two other tabs are instantly transmitted to the console.

1. First you will be asked to enter a character's name
2. To finish a conversation simply say "Bye" or "Goodbye" and you'll go back to step 1
3. Type "help" to see the list of available console commands

Example :
```
Who do do you want to talk to ?
you : Bob
You started talking with Bob !

you : Hello !
Bob : Hello, How are you ?
        
        ...
        
you : Bye !
Bob : Goodbye !

Who do you want to talk to ?
```