package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Takes shots efficiently based on previous shots
 */
public class Shooter {

  private List<List<Coord>> board = new ArrayList<>();
  private List<Coord> previousShots = new ArrayList<>();
  private List<Coord> targets = new ArrayList<>();
  private Random rand = new Random();

  /**
   * Constructor for testing
   *
   * @param shots   the list of previous shots
   * @param targets list of potential targets
   * @param board   board
   */
  public void setFields(List<Coord> shots, List<Coord> targets,
                        List<List<Coord>> board) {
    this.previousShots = shots;
    this.targets = targets;
    this.board = board;
    this.rand = new Random(4);
  }

  /**
   * Sets the size of this board to the given height and width
   *
   * @param height sets the height of the board
   * @param width  sets the width of the board
   */
  public void setSize(int height, int width) {
    for (int i = 0; i < height; i++) {
      board.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        board.get(i).add(new Coord(i, j));
      }
    }
  }

  /**
   * Updates the target list based on previous hits
   *
   * @param hits list of coords that were successfully hit
   */
  public void updateHits(List<Coord> hits) {
    for (Coord c : hits) {
      if (c.getY() > 0) {
        targets.add(board.get(c.getY() - 1).get(c.getX()));
      }
      if (c.getX() > 0) {
        targets.add(board.get(c.getY()).get(c.getX() - 1));
      }
      if (c.getY() < board.size() - 1) {
        targets.add(board.get(c.getY() + 1).get(c.getX()));
      }
      if (c.getX() < board.get(0).size() - 1) {
        targets.add(board.get(c.getY()).get(c.getX() + 1));
      }
    }
  }

  /**
   * Shoots for this round
   *
   * @param numShots the number of shots to take
   * @return returns the shots
   */
  public List<Coord> shoot(int numShots) {
    List<Coord> shots = new ArrayList<>();
    for (Coord c : targets) {
      if (!previousShots.contains(c) && numShots > 0) {
        shots.add(c);
        previousShots.add(c);
        numShots--;
      }
    }
    if (numShots > 0) {
      shots.addAll(shootRandom(numShots));
    }
    return shots;
  }

  /**
   * Takes random shots
   *
   * @param numShots the number of shots to take
   * @return returns the shots
   */
  public List<Coord> shootRandom(int numShots) {
    List<Coord> shots = new ArrayList<>();
    while (numShots > 0) {
      int height = rand.nextInt(board.size());
      int width = rand.nextInt(board.get(0).size());
      if (previousShots.size() >= board.size() * board.get(0).size()) {
        return shots;
      }
      if (!previousShots.contains(board.get(height).get(width))
          && (!isAdjacent(board.get(height).get(width))
          || (previousShots.size() > (board.size() * board.get(0).size()
          - (board.size() + board.get(0).size()) * 4)))) {
        shots.add(board.get(height).get(width));
        previousShots.add(board.get(height).get(width));
        numShots--;
      }
    }
    return shots;
  }

  /**
   * Returns if this coord is adjacent to a previously shot coord
   *
   * @param c the given coord
   * @return returns if this coord is adjacent to a previously shot coord
   */
  public boolean isAdjacent(Coord c) {
    if (c.getY() > 0
        && previousShots.contains(board.get(c.getY() - 1).get(c.getX()))) {
      return true;
    } else if (c.getX() > 0
        && previousShots.contains(board.get(c.getY()).get(c.getX() - 1))) {
      return true;
    } else if (c.getY() < board.size() - 1
        && previousShots.contains(board.get(c.getY() + 1).get(c.getX()))) {
      return true;
    } else if (c.getX() < board.get(0).size() - 1
        && previousShots.contains(board.get(c.getY()).get(c.getX() + 1))) {
      return true;
    }
    return false;
  }

}
