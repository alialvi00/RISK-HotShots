Milestone 2

Deliverables included in Milestone 2:
•	The code required for this milestone is packaged in a JAR file (including source files and executables) which will allow the game to be easily played on a different device. The code added in milestone 2 incorporates the View and the Controller of the MVC design pattern, allowing for users the play the game via a fully functional GUI. Unit tests using Junit for the Model are also included.
•	UML class diagrams and sequence diagrams, corresponding to the code and important features of the game will also be provided. The diagrams will also be located in the appendices of the documentation file.
•	The documentation, which simply encapsulates design decisions, the rules of RISK, and complete user manuals will also be submitted. 
•	All deliverables will be packaged into a well named zip-file.
Authors:
•	Ali Alvi
•	Areeb Haq
•	Hassan Jallad
•	Raj Sandhu

Changes Made Since the Previous Deliverable:

Milestone 1 introduced a playable, text-based version of RISK, that provided output from the console as text. The documentation described above, containing the UML and Sequence diagrams, design decisions and a user manual, was also created to allow players to have an understanding of the development methodologies used to create the game, and reasonings as to why certain design implementations and data structures were chosen.
Milestone 2 took the Model implementation provided in milestone 1 and added the View and Controller, creating a working GUI for users to play the game, abiding by the MVC design pattern. Junit tests based on the Model implementation of milestone 1 have also been provided, to help team members adhere to Test Driven Development (TDD) in future milestones. Other deliverables in milestone 1 such as the UML class diagrams, sequence diagrams and documentation including the user manual and design decisions, have been updated to reflect the changes made in milestone 2.

Known Issues:

•	Troops are unable to be maneuvered. This feature was not a requirement in milestone 2, so we chose not to implement it. This will be corrected in milestone 3.

Roadmap Ahead:

Essentially, in milestone 2, we created a working GUI to play RISK, following the MVC design pattern. We also created Junit tests as part of the Test-Driven Development practice for the model. We then updated the documentation UML class diagrams and sequence diagrams to reflect the changes made in milestone 2.
In milestone 3, we will implement a bonus army placement, which results in a player being granted additional troops for capturing an entire continent. We will also implement a troupe movement phase, which allows a player to move their troops from one of their captured countries to another, provided there is a path that connects them that contains only countries that the current player owns. The final implementation we will design is an AI player that plays the game by making the best possible move (many algorithms are being considered to implement this such as the Monte Carlo Tree Search method). 
This will result in necessary changes that will be made and added to the UML diagrams, documentation and Junit test classes. We will also implement the feedback provided to us by the TA assigned to our group.

