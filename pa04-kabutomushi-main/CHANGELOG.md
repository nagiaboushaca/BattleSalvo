* Fixed everything from pa03, setup, takeShots, reportDamage, and successfulHits
  * Because it wasn't working for pa03
* Added Board interface and BoardImpl class that implements it
  * Because previously we were handling the board in the player class, violating SOLID
* Moved the placing to the BoardImpl class and delegated a lot of setup tasks to it
  * So that the setup can happen in the board
* Changed the view to only have displayMessage and displayBoard and displayEndgame
  * So the prompting can happen in the controller
* Put the scanner in the controller
  * For easier passing of information
* Changed the constructors of all classes to fit SOLID
  * So that all objects can update each other
* Added horizontal/vertical and length fields to ship class
  * To better work with the JSON definitions
* Added ProxyPlayer and ProxyController
  * For PA04 implementation
* Also, our coordinate system was flipped, so we just had to reparse when sending info to the server