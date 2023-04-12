package wfc;

import junit.framework.TestCase;

public class RepositoryTest extends TestCase {

  public void testGetTimeTable() {
    Repository repository = new Repository();
    assertEquals(32, repository.getTimeTable().size());
  }

  public void testGetCustomerList() {
    Repository repository = new Repository();

    assertEquals(4, repository.getCustomerList().size());
  }
}