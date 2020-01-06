# Smart Interact
### PNS-Innov team C

---

![Jenkins](https://img.shields.io/badge/Jenkins-v2.164.1-brightgreen.svg)
![Artifactory](https://img.shields.io/badge/Artifactory-v6.8.7-brightgreen.svg)

Table of contents
===

- [Table of contents](#Table-of-contents)
- [Project presentation](#Project-presentation)
    - [Team C](#Team-C)
    - [Project subject](#Project-subject)
- [Project architecture](#Project-architecture)
    - [Component diagram](#Component-diagram)
    - [Dependencies](#Dependencies)
    - [References to sub-modules](#References-to-sub-modules)
- [Releases](#Releases)

Project presentation
===
    
Team C
---

- Alexandre Bolot
- Laura Lopez
- Alexanne Masson
- Théos Mariani

Project subject
---

Making a game-engine to include Natural Language Processing in video games.

Video-games can interact with our solution by sending the player's message, and we send back the appropriate reply.

We target two types of users :

- Game Designer (working on dialogues, script and characters)
Their job is to create `Characters`, give them some `Knowledge`, test if they match the expected behaviour and monitor the `Character's` reactions (what's in their mind)

- Game Developper (coding the rest of the game)
Their job is to plug our solution to the game they create, no matter the coding language they use (`C, C++, Java, Python, etc`)

```
Important : the video-game players are NOT our direct users.
We provide a tool for companies to create games on their own and sell them to players.
```

Project architecture
===

Component Diagram
---

![Component_Diagram](https://github.com/PNS-PS7-1819/pns-innov1819-new-pns-innov1819-c-smartinteract/blob/develop/Component_Diagram.png)
    
Dependencies
---

- `Designer Tool`
  - `PrepareGame`
  - `Monitor`
  - `ProcessInteraction`
  
- `ProcessInteraction`
  - `AnalyseSyntax`
  - `ManageJson`
  - `GenerateAnswer`
   
- `PrepareGame`, `Monitor` and `GenerateAnswer`
  - `ManageJson`

References to sub modules
---

Some of the git submodule links displayed on the the repository front page are broken and impossible to fix without puting the project structure at risk.

Therefore please use the following up-to-date links :


| Sub module | Jenkins |
| ---------- | ------- |
| [Analyse Syntax](https://github.com/PNS-PS7-1819/AnalyseSyntax-c/) | [![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=AnalyseSyntax-c)](http://51.83.69.199:8080/job/AnalyseSyntax-c/) |
| [Designer Tool](https://github.com/PNS-PS7-1819/DesignerTool-c/) | [![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=DesignerTool-c)](http://51.83.69.199:8080/job/DesignerTool-c/) |
| [Generate Answer](https://github.com/PNS-PS7-1819/GenerateAnswer-c/) | [![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=GenerateAnswer-c)](http://51.83.69.199:8080/job/GenerateAnswer-c/) |
| [Manage Json](https://github.com/PNS-PS7-1819/ManageJson-c/) | [![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=ManageJson-c)](http://51.83.69.199:8080/job/ManageJson-c/) |
| [Monitor](https://github.com/PNS-PS7-1819/Monitor-c/) | [![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=Monitor-c)](http://51.83.69.199:8080/job/Monitor-c/) |
| [Prepare Game](https://github.com/PNS-PS7-1819/PrepareGame-c/) | [![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=PrepareGame-c)](http://51.83.69.199:8080/job/PrepareGame-c/) |
| [Process Interaction](https://github.com/PNS-PS7-1819/ProcessInteraction-c/) | [![Build Status](http://51.83.69.199:8080/buildStatus/icon?job=ProcessInteraction-c)](http://51.83.69.199:8080/job/ProcessInteraction-c/) |

Releases
===

As we are working with a Scrum Agile method, here are the Sprint-release dates and associated (if existing) release tags :


|   Sprint   |    Due date    |     Tag      |
| :--------: | :------------: | :----------: |
|     1      |  `28/05/2019`  |  `SPRINT1`   |
|     2      |  `06/06/2019`  |  `SPRINT2`   |
|     3      |  `13/06/2019`  |   *`n/a`*    |