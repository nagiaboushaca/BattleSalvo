package cs3500.pa04.controller;

import cs3500.pa04.model.AbstractPlayer;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * class to validate data received from the user
 */
public class DataValidator {

  /**
   * returns true if the board size is valid
   *
   * @param height the height of the board
   *
   * @param width the width of the board
   *
   * @return true if the board size is valid
   */
  public static boolean validBoardSize(int height, int width) {
    return height >= 6 && width >= 6
        && height <= 10 && width <= 10;
  }

  /**
   * return true if the specifications are valid
   *
   * @param maxFleetSize max fleet size
   *
   * @param numCarriers number of carriers
   *
   * @param numBattleships number of battleships
   *
   * @param numDestroyers number of destroyers
   *
   * @param numSubmarines number of submarines
   *
   * @return true if the specifications are valid
   */
  public static boolean validSpecifications(int maxFleetSize,
      int numCarriers, int numBattleships, int numDestroyers, int numSubmarines) {
    return numCarriers >= 1
        && numBattleships >= 1
        && numDestroyers >= 1
        && numSubmarines >= 1
        && numCarriers + numBattleships +  numDestroyers + numSubmarines <= maxFleetSize;
  }

  /**
   * returns true if the shots given by the user are valid shots (i.e. haven't been shot at before)
   *
   * @param opponentBoard the user's info on the opponent's board
   *
   * @param shots the list of shots by the user
   *
   * @return true if the shots are valid
   */
  public static boolean validShots(Board opponentBoard, List<Coord> shots) {
    for (Coord c : shots) {
      if (c.getX() >= opponentBoard.getBoard().length
          || c.getY() >= opponentBoard.getBoard()[0].length) {
        return false;
      }
      if (c.getX() < 0 || c.getY() < 0) {
        return false;
      }
      if (opponentBoard.getBoard()[c.getX()][c.getY()].isAlreadyShot()) {
        return false;
      }
    }
    return true;
  }
}
