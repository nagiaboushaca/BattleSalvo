import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.controller.DataValidator;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.BoardImpl;
import cs3500.pa04.model.Coord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * to test the data validator
 */
public class DataValidatorTest {

  /**
   * to test board size
   */
  @Test
  public void testBoardSize() {
    boolean result = DataValidator.validBoardSize(6, 6);
    assertTrue(result);
    boolean result1 = DataValidator.validBoardSize(4, 8);
    assertFalse(result1);

  }


  /**
   * to test valid specifications
   */
  @Test
  public void testValidSpecifications() {
    boolean result = DataValidator.validSpecifications(10, 2,
        2, 2, 2);
    assertTrue(result);
    boolean result1 = DataValidator.validSpecifications(10, 3,
        4, 1, 2);
    assertTrue(result1);
    boolean result2 = DataValidator.validSpecifications(10, 3,
        4, 1, 2);
    assertTrue(result2);
  }


  /**
   * to test valid shots
   */
  @Test
  public void testValidShots() {
    Board opponentBoard = new BoardImpl();
    opponentBoard.createBoard(10, 10);
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(5, 5));
    boolean result = DataValidator.validShots(opponentBoard, shots);
    assertTrue(result);
    Board opponentBoard1 = new BoardImpl();
    opponentBoard1.createBoard(10, 10);
    opponentBoard1.getBoard()[0][0].setAlreadyShot();
    List<Coord> shots1 = new ArrayList<>();
    shots1.add(new Coord(0, 0));
    shots1.add(new Coord(11, 5));
    boolean result1 = DataValidator.validShots(opponentBoard1, shots1);
    assertFalse(result1);
    Board opponentBoard2 = new BoardImpl();
    opponentBoard2.createBoard(10, 10);
    opponentBoard2.getBoard()[0][0].setAlreadyShot();
    List<Coord> shots2 = new ArrayList<>();
    shots2.add(new Coord(15, 150));
    shots2.add(new Coord(11, 5));
    boolean result2 = DataValidator.validShots(opponentBoard2, shots2);
    assertFalse(result2);
    Board opponentBoard3 = new BoardImpl();
    opponentBoard3.createBoard(10, 10);
    opponentBoard3.getBoard()[0][0].setAlreadyShot();
    List<Coord> shots3 = new ArrayList<>();
    shots3.add(new Coord(-1, -3));
    shots3.add(new Coord(11, 5));
    boolean result3 = DataValidator.validShots(opponentBoard3, shots3);
    assertFalse(result3);

  }

}
