package wfc;

import junit.framework.TestCase;

public class ServiceTest extends TestCase {

  Service service = new Service(new Repository());

  public void testgetOptionalCustomer() {

    assertEquals("John", service.getOptionalCustomer("001").get().name);
  }

  public void testLiCustomer() {
    assertEquals(4, service.getCustomerList().size());
  }
}