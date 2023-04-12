package wfc;

import junit.framework.TestCase;

public class FitnessTypeTest extends TestCase {

  public void testGetCost() {
    FitnessType type = FitnessType.Spin;
    assertEquals(3000.0, type.getCost());
  }
}