Milestone 4

Deliverables included in Milestone 4:

•	The code required for this milestone is packaged in a JAR file (including source files and executables) which will allow the game to be easily played on a different device. 

The code added in milestone 4 incorporates two major features including the ability to save and load a game, as well as loading custom maps to play the game.

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

Milestone 3 Took the MVC implementation of the game and enhanced by introducing functionality such as bonus army placement, troupe movement and an “AI” player. The unit tests, documentation, UML class diagram and Sequence diagrams were all updated to reflect these changes, in particular, the user manual of the documentation which explains how to play the game.

Milestone 4 took the game designed in the previous milestones and provided additional functionality such as save/load capabilities and the ability to play the game through the use of a custom map. The unit tests, documentation, UML class diagram and Sequence diagrams were all updated to reflect these changes, in particular, the user manual of the documentation which explains how to play the game.

Known Issues:
•	Once you click the Attack button, you cannot change your mind to pass the turn off to the next player.
•	Due to the size and statistical probabilities of the game, the AI player may take a while to finish playing the game.

Roadmap Ahead:

In milestone 4, we added enhancements to the game, as well as improving upon the existing code, making it more robust and smell free. The first feature that was added was save/load functionality. This was implemented using Java Serialization to serialize the model component of the game, storing the class objects in a text file. The second feature that was implemented allows a user to load a custom map into the game, and the game is able to reject invalid maps where countries could be missing or unreachable. The custom map was defined by using JSON format.

This will result in necessary changes that will be made and added to the UML diagrams, documentation and Junit test classes. We will also implement the feedback provided to us by the TA assigned to our group, including splitting up the playAI() function to follow low coupling and high cohesion principles.
Milestone 4 is the last iteration of the project, and as such, there is no formal roadmap ahead. What is likely to be done, is that we will utilize the feedback provided to us in Milestone 4 by the TA assigned to our group, to make further enhancements to our game, for the sake of knowledge and learning.

