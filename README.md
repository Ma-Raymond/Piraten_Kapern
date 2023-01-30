# A1 - Piraten Karpen

  * Author: Raymond Ma
  * Email: mar47@mcmaster.ca

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode:
    * `mvn -q exec:java` (here, `-q` tells maven to be _quiet_)
    * `mvn -q exec:java -Dexec.args="combo random trace"` also works with arguments
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar` 

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * < Your DoD goes here >

### Backlog 

| MVP? | Id  |                      Feature                      | Status |  Started   | Delivered  |
|:----:|:---:|:-------------------------------------------------:|:------:|:----------:|:----------:|
|  x   | F01 |                    Roll a dice                    |   D    |  01/01/23  | 01/16/2023 |
|  x   | F02 |                 Roll eight dices                  |   D    | 01/18/2023 | 01/19/2023 |
|  x   | F03 |           Select type of player in args           |   D    | 01/20/2023 | 01/22/2023 |
|  x   | F04 |           end of game with three cranes           |   D    | 01/20/2023 | 01/22/2023 | 
|  x   | F05 |     Player keeping random dice at their turn      |   D    | 01/20/2023 | 01/22/2023 |
|  x   | F06 |             Score points: 3-of-a-kind             |   D    | 01/25/2023 | 01/25/2023 | 
|  x   | F07 |               Treasure Chest Points               |   D    | 01/25/2023 | 01/25/2023 |
|  x   | F08 |  Create Combo Strategy Option (Maximize Combos)   |   D    | 01/25/2023 | 01/25/2023 |
|  x   | F09 |          At least 2 rolls dice each time          |   D    | 01/24/2023 | 01/25/2023 |
|  x   | F10 |          Start with Cards and add value           |   D    | 01/24/2023 | 01/25/2023 |
|  x   | F11 | Make a trace mode for development as an argument  |   D    | 01/24/2023 | 01/26/2023 |
|  x   | F12 |              Create a Deck of Cards               |   D    | 01/25/2023 | 01/26/2023 |
|  x   | F13 |             Shuffle the Deck of Cards             |   D    | 01/25/2023 | 01/26/2023 |
|  x   | F14 |          Pick out a Card from Card PILE           |   D    | 01/25/2023 | 01/26/2023 |
|  x   | F15 |         Created Sea Battle card into Game         |   D    | 01/25/2023 | 01/26/2023 |
|  x   | F16 |         Implement Sea Battle point system         |   D    | 01/25/2023 | 01/26/2023 |
|  x   | F17 |        Create Sea Battle Strategy for User        |   D    | 01/25/2023 | 01/26/2023 |
|  x   | F18 |      Created Monkey Business Card into Game       |   D    | 01/25/2023 | 01/26/2023 |
|  x   | F19 |     Create Monkey Business Strategy for User      |   D    | 01/25/2023 | 01/26/2023 |
|  x   | F20 | Implement Monkey Business Card into Points System |   D    | 01/25/2023 | 01/26/2023 |
| ...  | ... |                        ...                        |        |

