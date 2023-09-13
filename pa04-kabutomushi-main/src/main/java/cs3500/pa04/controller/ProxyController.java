package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.GameType;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.SpecJson;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.BoardImpl;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * controller to deal with proxy pattern
 */
public class ProxyController implements Controller {
  private final Socket server;
  private InputStream in;
  private PrintStream out;
  private final Player player;
  private Board playerBoard;
  private Board opponentBoardInfo;
  private final ObjectMapper mapper = new ObjectMapper();
  private boolean gameOver = false;

  /**
   * constructor for the ProxyController
   *
   * @param p1 the ai player playing
   * @param s  the socket to connect to the server
   */
  public ProxyController(Player p1, Board playerBoard, Board opponentBoardInfo, Socket s) {
    this.server = s;
    this.player = p1;
    this.playerBoard = playerBoard;
    this.opponentBoardInfo = opponentBoardInfo;

    try {
      this.in = server.getInputStream();
    } catch (IOException e) {
      System.out.println("server inputStream error");
      System.out.println(e.getMessage());
    }
    try {
      this.out = new PrintStream(server.getOutputStream());
    } catch (IOException e) {
      System.out.println("server outputStream error");
      System.out.println(e.getMessage());
    }
  }

  /**
   * method that runs the program
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!gameOver) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        this.delegateMessage(message);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Determines the type of request the server has sent ("run" or "setup" or "take-shots" etc.)
   * and delegates to the corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    switch (name) {
      case "join": {
        handleJoin();
        break;
      }
      case "setup": {
        handleSetup(arguments);
        break;
      }
      case "take-shots": {
        handleTakeShots();
        break;
      }
      case "report-damage": {
        handleReportDamage(arguments);
        break;
      }
      case "successful-hits": {
        handleSuccessfulHits(arguments);
        break;
      }
      case "end-game": {
        handleEndGame(arguments);
        break;
      }
      default: {
        break;
      }
    }
  }

  /**
   * handles join, sends a response for name and gametype
   */
  private void handleJoin() {
    JoinJson joinJson = new JoinJson(this.player.name(), GameType.SINGLE);
    JsonNode response = JsonUtils.serializeRecord(joinJson);
    MessageJson message = new MessageJson("join", response);
    JsonNode jsonMessage = JsonUtils.serializeRecord(message);

    this.out.println(jsonMessage);
    this.out.flush();
  }

  /**
   * handles setup and sends a response for the setup
   *
   * @param arguments the height, width, and specifications
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupInfo = this.mapper.convertValue(arguments, SetupJson.class);
    int width = setupInfo.width();
    int height = setupInfo.height();
    SpecJson spec = setupInfo.spec();

    Map<ShipType, Integer> specsMap = new LinkedHashMap<>();
    specsMap.put(ShipType.Carrier, spec.numCarrier());
    specsMap.put(ShipType.Battleship, spec.numBattleship());
    specsMap.put(ShipType.Destroyer, spec.numDestroyer());
    specsMap.put(ShipType.Submarine, spec.numSubmarine());

    List<Ship> shipList = this.player.setup(height, width, specsMap);

    Ship[] shipArray = shipList.toArray(Ship[]::new);

    List<ShipJson> shipJsonList = new ArrayList<>();

    for (Ship s : shipArray) {
      CoordJson shipCoord = new CoordJson(s.getCoord().getX(), s.getCoord().getY());
      ShipJson jsonShip = new ShipJson(shipCoord, s.getLength(), s.getDirection());
      shipJsonList.add(jsonShip);
    }

    FleetJson fleet = new FleetJson(shipJsonList.toArray(ShipJson[]::new));

    JsonNode jsonResponse = JsonUtils.serializeRecord(fleet);
    MessageJson message = new MessageJson("setup", jsonResponse);

    this.out.println(JsonUtils.serializeRecord(message));
    this.out.flush();
  }

  /**
   * handles takeShots information and sends a response
   */
  private void handleTakeShots() {
    List<Coord> shots = this.player.takeShots();

    List<CoordJson> jsonShots = new ArrayList<>();
    for (Coord c : shots) {
      CoordJson coord = new CoordJson(c.getY(), c.getX());
      jsonShots.add(coord);
    }

    CoordinatesJson jsonCoords = new CoordinatesJson(jsonShots.toArray(CoordJson[]::new));

    JsonNode jsonResponse = JsonUtils.serializeRecord(jsonCoords);
    MessageJson message = new MessageJson("take-shots", jsonResponse);

    this.out.println(JsonUtils.serializeRecord(message));
    this.out.flush();
  }

  /**
   * handles report damage and sends a response
   *
   * @param arguments coordinates that the opponent shot
   */
  private void handleReportDamage(JsonNode arguments) {
    CoordinatesJson shots = this.mapper.convertValue(arguments, CoordinatesJson.class);

    List<Coord> coordList = new ArrayList<>();

    for (CoordJson c : shots.coordinates()) {
      coordList.add(new Coord(c.valueY(), c.valueX()));
    }

    List<Coord> successfulHits = this.player.reportDamage(coordList);

    List<CoordJson> jsonHits = new ArrayList<>();
    for (Coord c : successfulHits) {
      CoordJson coord = new CoordJson(c.getY(), c.getX());
      jsonHits.add(coord);
    }

    CoordinatesJson jsonCoords = new CoordinatesJson(jsonHits.toArray(CoordJson[]::new));

    JsonNode jsonResponse = JsonUtils.serializeRecord(jsonCoords);
    MessageJson message = new MessageJson("report-damage", jsonResponse);

    this.out.println(JsonUtils.serializeRecord(message));
    this.out.flush();
  }

  /**
   * handles successfulHits and sends an empty response
   *
   * @param arguments the successful hits on opponent ships
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    CoordinatesJson shots = this.mapper.convertValue(arguments, CoordinatesJson.class);
    List<Coord> coordList = new ArrayList<>();

    for (CoordJson c : shots.coordinates()) {
      coordList.add(new Coord(c.valueX(), c.valueY()));
    }

    this.player.successfulHits(coordList);

    MessageJson message =
        new MessageJson("successful-hits", this.mapper.createObjectNode());

    this.out.println(JsonUtils.serializeRecord(message));
    this.out.flush();
  }

  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameInfo = this.mapper.convertValue(arguments, EndGameJson.class);

    GameResult result = null;
    switch (endGameInfo.result()) {
      case "WIN":
        result = GameResult.PlayerWin;
        break;
      case "LOSE":
        result = GameResult.PlayerLose;
        break;
      case "DRAW":
        result = GameResult.Draw;
        break;
      default:
        break;
    }
    String reason = endGameInfo.reason();

    this.player.endGame(result, reason);

    MessageJson message = new MessageJson("end-game", this.mapper.createObjectNode());

    this.out.println(JsonUtils.serializeRecord(message));
    this.out.flush();

    try {
      this.server.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
