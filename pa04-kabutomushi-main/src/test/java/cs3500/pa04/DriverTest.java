package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * tester for the driver
 */
class DriverTest {

  /**
   * tester for the driver
   */
  @Test
  public void driverTest() {
    Driver d = new Driver();
    String[] args2 = new String[]{"i"};
    assertThrows(
        IllegalArgumentException.class,
        () -> Driver.main(args2)
    );
  }


}