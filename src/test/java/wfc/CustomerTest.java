package wfc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {
  Lesson lesson = new Lesson("001", "8am", "Saturday", FitnessType.Spin);
  Lesson lesson1 = new Lesson("002", "8am", "Saturday", FitnessType.Spin);
  Customer customer = new  Customer();

  @Test
  public void addLesson() {
    customer.addLesson(lesson);
    customer.addLesson(lesson1);
    assertEquals(2, customer.getLessons().size());
  }

  @Test
  public void removeLesson() {
    customer.addLesson(lesson);
    customer.addLesson(lesson1);
    customer.removeLesson(lesson1);
    assertEquals(1, customer.getLessons().size());
  }
}