package cs3500.pa04.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Shooter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the shooter class
 */
public class ShooterTest {
  private Shooter shooter;
  private final List<Coord> previousShots = new ArrayList<>();
  private final List<Coord> targets = new ArrayList<>();
  private final List<List<Coord>> board = new ArrayList<>();

  /**
   * Initializes the fields for testing
   */
  @BeforeEach
  public void initFields() {
    shooter = new Shooter();
    shooter.setFields(previousShots, targets, board);
    shooter.setSize(6, 6);
  }

  @Test
  public void checkUpdateHits() {
    shooter.updateHits(new ArrayList<>(Arrays.asList(
        new Coord(0, 0), new Coord(5, 5)
    )));
    assertEquals(targets, new ArrayList<>(Arrays.asList(
        board.get(1).get(0), board.get(0).get(1), board.get(4).get(5), board.get(5).get(4)
    )));
  }

  @Test
  public void checkShoot() {
    shooter.updateHits(new ArrayList<>(Arrays.asList(
        new Coord(0, 0), new Coord(5, 5)
    )));
    assertEquals(shooter.shoot(3), new ArrayList<>(Arrays.asList(
        board.get(1).get(0), board.get(0).get(1), board.get(4).get(5))));

  }

  @Test
  public void checkShootRandom() {
    assertEquals(shooter.shootRandom(1), List.of(board.get(2).get(4)));
    for (int i = 0; i < 50; i++) {
      previousShots.add(new Coord(0, 0));
    }
    assertEquals(shooter.shootRandom(27), new ArrayList<>());
  }

  @Test
  public void checkIsAdjacent() {
    shooter.shoot(7);
    assertTrue(shooter.isAdjacent(board.get(0).get(0)));
    assertFalse(shooter.isAdjacent(board.get(0).get(1)));
    assertTrue(shooter.isAdjacent(board.get(0).get(2)));
    assertFalse(shooter.isAdjacent(board.get(0).get(5)));
    assertFalse(shooter.isAdjacent(board.get(5).get(4)));
    assertFalse(shooter.isAdjacent(board.get(5).get(3)));
    assertFalse(shooter.isAdjacent(board.get(5).get(2)));
    assertFalse(shooter.isAdjacent(board.get(5).get(1)));
    assertFalse(shooter.isAdjacent(board.get(5).get(0)));
  }


}
