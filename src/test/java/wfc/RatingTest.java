package wfc;

import junit.framework.TestCase;
import org.junit.Test;

public class RatingTest extends TestCase {

  @Test
  public void testToRating() {
    Rating rating = Rating.toRating(5);
    assertEquals(Rating.V_Satisfied, rating);
  }
}