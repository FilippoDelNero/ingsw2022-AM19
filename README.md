<h1 align="center"> 
  Prova Finale di Ingegneria del Software - A.A. 2021/22
</h1>
<h3 align="center">
  Prof. Alessandro Margara - Politecnico di Milano
</h3>
<h4 align="center">
  Gruppo AM19
</h4>

### Members:
* #### 10705522     Laura Colazzo ([@lauracolazzo](https://github.com/lauracolazzo)) <br>laura.colazzo@mail.polimi.it
* #### 10705035     Dennis De Maria ([@dennydemaria](https://github.com/dennydemaria)) <br>dennis.demaria@mail.polimi.it
* #### 10662783     Filippo Del Nero ([@FilippoDelNero](https://github.com/FilippoDelNero)) <br>filippogiovanni.delnero@mail.polimi.it
<br>

### Progress:


| Functionality          |State                                        |
|:-----------------------|:-------------------------------------------:|
| Basic rules            | 🟢 |
| Complete rules         | 🟢 |
| Communication Protocol | 🟢 |
| GUI                    | 🟢 |
| CLI                    | 🟢 |
| AF 1: Character cards  | 🟢 |
| AF 2: Persistence      | 🟢 |
| AF 3: -                | 🔴 |


🔴 Uninitiated
🟢 Completed
🟡 Work in progress

## Test coverage

| Package     | Class coverage | Method coverage | Line Coverage |
|:------------|:--------------:|:---------------:|:-------------:|
| Model       |      100%      |       85%       |      88%      |
| Controller* |      75%       |       41%       |      20%      |

*Tests were made only where network was not involved

## Execution
### Server
To run the application in server mode you need to write the following command:
```
java -jar AM19.jar -s [port number]
```

### Client - CLI
To run the application as client in CLI mode you need to write the following command:
```
java -jar AM19.jar -cli 
```

### Client - GUI
To run the application as client in GUI mode you can double click on the .jar file or you can write the following command:
```
java -jar AM19.jar
```

### Application design
The application supports complete rules on both CLI e GUI. It also supports two advanced functionalities, that are all 12 character cards implementation and persistence.


Regarding persistence, the functionality was implemented to save game state at the end of every round. It means that the first saving happens only once the login phase has ended and all players played their first round.
When a complete match is finished, the stored match is automatically deleted.


