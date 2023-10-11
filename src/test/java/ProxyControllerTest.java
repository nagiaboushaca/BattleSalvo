import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.GameType;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.SpecJson;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.BoardImpl;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class to test ProxyController
 */
public class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;

  /**
   * sets up the output stream
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Check that the server is being sent the correct join message
   */
  @Test
  public void testJoinJson() {
    // Prepare sample message
    JoinJson joinJson = new JoinJson("kabutomushi", GameType.SINGLE);
    JsonNode sampleMessage = createSampleMessage("join", joinJson);

    // Create the client with all necessary messages
    Mocket mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    Board aiBoard = new BoardImpl();
    Board userBoardInfo = new BoardImpl();
    Player aiPlayer = new AiPlayer(aiBoard, userBoardInfo);

    this.controller = new ProxyController(aiPlayer, aiBoard, userBoardInfo, mocket);

    // run the dealer and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"join\",\"arguments\":"
            + "{\"name\":\"kabutomushi\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * tests handle take shots
   */
  @Test
  public void testHandleTakeShots() {

    // Prepare sample message (this is random placeholder info
    JsonNode sampleMessage =
        createSampleMessage("take-shots", new CoordJson(1, 2));

    // Create the client with all necessary messages
    Mocket mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    Board aiBoard = new BoardImpl();
    Board userBoardInfo = new BoardImpl();
    Player aiPlayer = new AiPlayer(aiBoard, userBoardInfo);

    this.controller = new ProxyController(aiPlayer, aiBoard, userBoardInfo, mocket);

    // run the dealer and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\"";
    assertTrue(logToString().startsWith(expected));
  }

  /**
   * tests handleSetup
   */
  @Test
  public void testSetupJson() {

    // Prepare sample message
    SetupJson setupJson =
        new SetupJson(6, 6, new SpecJson(1, 2, 2, 1));
    JsonNode sampleMessage = createSampleMessage("setup", setupJson);

    // Create the client with all necessary messages
    Mocket mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    Board aiBoard = new BoardImpl();
    Board userBoardInfo = new BoardImpl();
    Player aiPlayer = new AiPlayer(aiBoard, userBoardInfo);

    this.controller = new ProxyController(aiPlayer, aiBoard, userBoardInfo, mocket);

    // run the dealer and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\"";
    assertTrue(logToString().startsWith(expected));
  }

  /**
   * tests handle report damage
   */
  @Test
  public void testHandleReportDamage() {
    CoordJson[] coord = new CoordJson[2];
    coord[0] = new CoordJson(1, 1);
    coord[1] = new CoordJson(2, 2);

    CoordinatesJson coords = new CoordinatesJson(coord);

    // Prepare sample message (this is random placeholder info
    JsonNode sampleMessage =
        createSampleMessage("report-damage", coords);

    // Create the client with all necessary messages
    Mocket mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    Board aiBoard = new BoardImpl();
    Board userBoardInfo = new BoardImpl();
    Player aiPlayer = new AiPlayer(aiBoard, userBoardInfo);

    this.controller = new ProxyController(aiPlayer, aiBoard, userBoardInfo, mocket);

    // run the dealer and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\"";
    assertTrue(logToString().startsWith(expected));
  }

  /**
   * tests handle successful hits
   */
  @Test
  public void testHandleSuccessfulHits() {
    CoordJson[] coord = new CoordJson[2];
    coord[0] = new CoordJson(1, 1);
    coord[1] = new CoordJson(2, 2);

    CoordinatesJson coords = new CoordinatesJson(coord);

    // Prepare sample message (this is random placeholder info
    JsonNode sampleMessage =
        createSampleMessage("successful-hits", coords);

    // Create the client with all necessary messages
    Mocket mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    Board aiBoard = new BoardImpl();
    Board userBoardInfo = new BoardImpl();
    Player aiPlayer = new AiPlayer(aiBoard, userBoardInfo);

    this.controller = new ProxyController(aiPlayer, aiBoard, userBoardInfo, mocket);

    // run the dealer and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"successful-hits\",\"arguments\":{}}\n";

    assertEquals(expected, logToString());
  }

  /**
   * tests handle end game
   */
  @Test
  public void testHandleEndGame() {

    EndGameJson endGameJson = new EndGameJson("WIN", "You sank all ships");

    // Prepare sample message (this is random placeholder info
    JsonNode sampleMessage =
        createSampleMessage("end-game", endGameJson);

    // Create the client with all necessary messages
    Mocket mocket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    Board aiBoard = new BoardImpl();
    Board userBoardInfo = new BoardImpl();
    Player aiPlayer = new AiPlayer(aiBoard, userBoardInfo);

    this.controller = new ProxyController(aiPlayer, aiBoard, userBoardInfo, mocket);

    // run the dealer and verify the response
    this.controller.run();

    String expected =
        "{\"method-name\":\"end-game\",\"arguments\":{}}\n";

    assertEquals(expected, logToString());
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   *
   * @param messageObject object to embed in a message json
   *
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }
}
