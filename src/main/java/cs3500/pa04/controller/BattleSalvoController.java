package cs3500.pa04.controller;

import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.BoardImpl;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.view.View;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * controller that runs the BattleSalvo program
 */
public class BattleSalvoController implements Controller {
  private Player user;
  private Player ai;
  private Board userBoard;
  private Board aiBoard;
  private Board opponentBoardInfo;
  private View view;
  private Readable input;
  private Scanner scanner;
  private int height;
  private int width;
  private Map<ShipType, Integer> specifications;
  private static List<Coord> shotInputs;
  private boolean gameOver;
  private GameResult result;

  /**
   * constructor
   *
   * @param user the manual player
   *
   * @param ai the ai player
   *
   * @param userBoard the user's board
   *
   * @param aiBoard the ai player's board
   *
   * @param view the view to display the application
   *
   * @param input the readable input
   *
   * @param opponentBoardInfo the known opponent board from the user
   */
  public BattleSalvoController(
      Player user, Player ai, Board userBoard,
      Board aiBoard, View view, Readable input, Board opponentBoardInfo) {
    this.user = user;
    this.ai = ai;
    this.userBoard = userBoard;
    this.aiBoard = aiBoard;
    this.opponentBoardInfo = opponentBoardInfo;
    this.view = view;
    this.input = input;
    this.gameOver = false;
    this.scanner = null;
    this.specifications = new LinkedHashMap<>();
  }

  /**
   * runs the BattleSalvo game
   */
  public void run() {
    this.scanner = new Scanner(this.input);
    view.displayMessage("Welcome to BattleSalvo!!");
    this.promptBoardSize();
    this.promptSpecifications();

    List<Ship> userShips = this.user.setup(height, width, specifications);
    List<Ship> aiShips = this.ai.setup(height, width, specifications);

    while (!gameOver) {
      view.displayMessage("Your board: ");
      view.displayBoard(this.userBoard.getBoard());

      view.displayMessage("Opponent's board data: ");
      view.displayMessage("H = hit, M = miss, 0 = no info yet");
      view.displayBoard(this.opponentBoardInfo.getBoard());

      promptShotInputs();

      List<Coord> successfulOpponentShots = this.user.reportDamage(this.ai.takeShots());
      List<Coord> successfulPlayerShots = this.ai.reportDamage(this.user.takeShots());
      this.user.successfulHits(successfulPlayerShots);
      this.ai.successfulHits(successfulOpponentShots);

      if (userShips.size() == 0 || aiShips.size() == 0) {
        this.calculateGameResult();
        this.gameOver = true;
      }
    }

    view.displayEndGame(this.result);
  }

  /**
   * prompts the user to enter board size and reprompts if they are invalid, stores data
   */
  private void promptBoardSize() {
    view.displayMessage("Please enter a valid height and width of the game below: ");
    view.displayMessage("Example: 6 8 ");
    this.height = scanner.nextInt();
    this.width = scanner.nextInt();

    if (!DataValidator.validBoardSize(this.height, this.width)) {
      view.displayMessage("Oops! You have entered invalid dimensions.");
      view.displayMessage("Remember that both the height and width of the game "
          + "must between 6 and 10, inclusive");
      this.promptBoardSize();
    }
  }

  /**
   * prompts the user for ship specifications and reprompts if invalid, stores data
   */
  public void promptSpecifications() {
    view.displayMessage("Please enter your fleet in the order "
        + "[Carrier, Battleship, Destroyer, Submarine].");

    int maxFleetSize = Math.min(this.height, this.width);

    view.displayMessage("Remember, your fleet may not exceed size " + maxFleetSize);

    int numCarriers = scanner.nextInt();
    int numBattleships = scanner.nextInt();
    int numDestroyers = scanner.nextInt();
    int numSubmarines = scanner.nextInt();

    this.specifications.put(ShipType.Carrier, numCarriers);
    this.specifications.put(ShipType.Battleship, numBattleships);
    this.specifications.put(ShipType.Destroyer, numDestroyers);
    this.specifications.put(ShipType.Submarine, numSubmarines);

    if (!DataValidator.validSpecifications(maxFleetSize,
        numCarriers, numBattleships, numDestroyers, numSubmarines)) {
      System.out.println("Oops! You have entered invalid specifications.");
      this.specifications.clear();
      this.promptSpecifications();
    }
  }

  /**
   * prompts the user to enter shots and reprompts if invalid, stores data
   */
  public void promptShotInputs() {
    int numShots = this.userBoard.getNumAllowedShots();
    view.displayMessage("Please Enter " + numShots + " Shots: ");
    List<Coord> coords = new ArrayList<>();
    for (int i = 0; i < numShots; i++) {
      Coord coord = new Coord(scanner.nextInt(), scanner.nextInt());
      coords.add(coord);
    }
    if (this.userBoard.getShips().size() >= this.opponentBoardInfo.getYetToBeShotAt().size()) {
      shotInputs = new ArrayList<>();
      shotInputs.addAll(this.opponentBoardInfo.getYetToBeShotAt());
      return;
    }

    shotInputs = new ArrayList<>();
    shotInputs.addAll(coords);
    while (!DataValidator.validShots(this.opponentBoardInfo, coords)) {
      view.displayMessage("Some shots were invalid, or places you've shot at before. Try again: ");
      shotInputs.removeAll(coords);
      this.promptShotInputs();
    }
  }

  /**
   * calculates the game result and gameResult
   */
  public void calculateGameResult() {
    if (this.userBoard.getShips().size() == 0 && this.aiBoard.getShips().size() == 0) {
      this.result = GameResult.Draw;
    } else if (this.aiBoard.getShips().size() == 0) {
      this.result = GameResult.PlayerWin;
    } else {
      this.result = GameResult.PlayerLose;
    }
  }

  /**
   * static method to pass the user input shots to the player class
   *
   * @return the list of shots the player inputted
   */
  public static List<Coord> passShotInputs() {
    return shotInputs;
  }
}
