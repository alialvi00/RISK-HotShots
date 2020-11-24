Milestone 3

Deliverables included in Milestone 3:

•	The code required for this milestone is packaged in a JAR file (including source files and executables) which will allow the game to be easily played on a different device. The code added in milestone 3 incorporates the troop fortification strategy found in RISK: Global Domination, troupe movement functionality between the countries held by an attacking player, as well as an intelligent “AI” player.
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

Milestone 2 took the Model implementation provided in milestone 1 and added the View and Controller, creating a working GUI for users to play the game, abiding by the MVC design pattern. Junit tests based on the Model implementation of milestone 1 were also been provided, to help team members adhere to Test Driven Development (TDD). Other deliverables in milestone 1 such as the UML class diagrams, sequence diagrams and documentation including the user manual and design decisions were updated to reflect the changes made in milestone 2.

Milestone 3 Took the MVC implementation of the game and enhanced by introducing functionality such as bonus army placement, troupe movement and an “AI” player. The unit tests, documentation, UML class diagram and Sequence diagrams were all updated to reflect these changes the user manual of the documentation which explains how to play the game.

Known Issues:

•	Once you click the Attack button, you cannot change your mind to pass the turn off to the next player.
•	Due to the size and statistical probabilities of the game, the AI player may take a while to finish playing the game.

Roadmap Ahead:

In milestone 3, we implemented a bonus army placement, which results in a player being granted additional troops for capturing an entire continent. We also implemented a troupe movement feature, which allows a player to move their troops from one of their captured countries to another, provided there is a path that connects them that contains only countries that the current player owns. The final implementation we designed is an AI player that plays the game by making the best possible move. It is also important to note is that players can choose the number of AI players playing in a game. We then updated the Junit tests, UML diagrams and documentation to reflect these changes. 

In milestone 4, we will simply be adding enhancements to the game, as well as making it more robust and smell free. The first feature that will be added is a save/load function. This will most probably be implemented using Java Serialization. The second feature will allow a user to load a custom map into the game and the game will be able to reject invalid maps where countries could be missing or unreachable. The custom map will most likely be defined in XML or JSON format.
This will result in necessary changes that will be made and added to the UML diagrams, documentation, and Junit test classes. We will also implement the feedback provided to us by the TA assigned to our group.
