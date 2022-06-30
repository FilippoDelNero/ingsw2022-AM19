<h1 align="center"> 
  Prova Finale di Ingegneria del Software - A.A. 2021/22
</h1>
<h3 align="center">
  Prof. Alessandro Margara - Politecnico di Milano
</h3>
<h4 align="center">
  Gruppo AM19
</h4>



## Description
The project was part of the final evaluation of "Computer Science Engineering" degree course at Politecnico di Milano.

The specifications required to develop a Java application to simulate an existing board game by Cranio Creations called "Eriantys".

## Application design
The application supports complete rules on both CLI e GUI. It also supports two advanced functionalities, that are all 12 character cards implementation and persistence.


Regarding persistence, the functionality was implemented to save game state at the end of every round. It means that the first saving happens only once the login phase has ended and all players played their first round.
Information about an in progress match are stored in the same directory as the .jar file. When a match is completed, the stored match is automatically deleted.

In case a game is interrupted due tue fatal errors occurring, server needs to be restarted before resuming the previous match or before starting a new one.



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

| Package     | Class coverage | Method coverage | Line coverage |
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