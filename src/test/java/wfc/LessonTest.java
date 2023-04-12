package wfc;

import junit.framework.TestCase;
import org.junit.Test;

public class LessonTest extends TestCase {
  Lesson lesson = new Lesson("001", "8am", "Saturday", FitnessType.Spin);
  @Test
  public void testAddCustomer() {
    Customer customer = new Customer("001", "me", "075445", "fsd");
    Customer customer2 = new Customer("002", "mer", "07544565", "fsd");
    lesson.addCustomer(customer);
    lesson.addCustomer(customer2);
    assertEquals(2, lesson.getCustomerList().size());
  }

  @Test
  public void testRemoveCustomer() {
    Customer customer = new Customer("001", "me", "075445", "fsd");
    Customer customer2 = new Customer("002", "mer", "07544565", "fsd");
    lesson.addCustomer(customer);
    lesson.addCustomer(customer2);
    lesson.removeCustomer(customer2);
    assertEquals(1, lesson.getCustomerList().size());
  }
}