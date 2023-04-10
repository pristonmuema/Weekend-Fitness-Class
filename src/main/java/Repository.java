import java.util.ArrayList;
import java.util.List;
import wfc.Customer;
import wfc.FitnessType;
import wfc.Lesson;

public class Repository {

  public static final String EIGHT_AM = "8.00 am";
  public static final String FIVE_PM = "5.00 pm";

  public static final String SATURDAY = "Saturday";

  public static final String SUNDAY = "Sunday";

  List<Customer> customerList = new ArrayList<>();
  List<Lesson> lessons = new ArrayList<>();

  public void addCustomers(Customer customer) {
    customerList.add(customer);
  }

  public List<Customer> getCustomerList() {
    addCustomers(new Customer("001", "John", "7876545678", "Street 001"));
    addCustomers(new Customer("002", "Phil", "7876545656", "Street 005"));
    addCustomers(new Customer("003", "Joe", "78765645678", "Street 007"));
    addCustomers(new Customer("004", "Dan", "7872345678", "Street 002"));
    return this.customerList;
  }

  public void addLesson(Lesson lesson) {
    lessons.add(lesson);
  }

  public List<Lesson> getTimeTable() {
    addLesson(new Lesson("A001", EIGHT_AM, SATURDAY, FitnessType.Aquacise));
    addLesson(new Lesson("S001", EIGHT_AM, SATURDAY, FitnessType.Spin));
    addLesson(new Lesson("Y001", EIGHT_AM, SATURDAY, FitnessType.Yoga));
    addLesson(new Lesson("Z001", EIGHT_AM, SATURDAY, FitnessType.Zumba));

    addLesson(new Lesson("A002", FIVE_PM, SATURDAY, FitnessType.Aquacise));
    addLesson(new Lesson("S002", FIVE_PM, SATURDAY, FitnessType.Spin));
    addLesson(new Lesson("Y002", FIVE_PM, SATURDAY, FitnessType.Yoga));
    addLesson(new Lesson("Z002", FIVE_PM, SATURDAY, FitnessType.Zumba));

    addLesson(new Lesson("A003", EIGHT_AM, SUNDAY, FitnessType.Aquacise));
    addLesson(new Lesson("S003", EIGHT_AM, SUNDAY, FitnessType.Spin));
    addLesson(new Lesson("Y003", EIGHT_AM, SUNDAY, FitnessType.Yoga));
    addLesson(new Lesson("Z002", EIGHT_AM, SUNDAY, FitnessType.Zumba));

    addLesson(new Lesson("A004", FIVE_PM, SUNDAY, FitnessType.Aquacise));
    addLesson(new Lesson("S004", FIVE_PM, SUNDAY, FitnessType.Spin));
    addLesson(new Lesson("Y004", FIVE_PM, SUNDAY, FitnessType.Yoga));
    addLesson(new Lesson("Z004", FIVE_PM, SUNDAY, FitnessType.Zumba));

    addLesson(new Lesson("A005", EIGHT_AM, SATURDAY, FitnessType.Aquacise));
    addLesson(new Lesson("S005", EIGHT_AM, SATURDAY, FitnessType.Spin));
    addLesson(new Lesson("Y005", EIGHT_AM, SATURDAY, FitnessType.Yoga));
    addLesson(new Lesson("Z005", EIGHT_AM, SATURDAY, FitnessType.Zumba));

    addLesson(new Lesson("A006", FIVE_PM, SATURDAY, FitnessType.Aquacise));
    addLesson(new Lesson("S006", FIVE_PM, SATURDAY, FitnessType.Spin));
    addLesson(new Lesson("Y006", FIVE_PM, SATURDAY, FitnessType.Yoga));
    addLesson(new Lesson("Z006", FIVE_PM, SATURDAY, FitnessType.Zumba));

    addLesson(new Lesson("A007", EIGHT_AM, SUNDAY, FitnessType.Aquacise));
    addLesson(new Lesson("S007", EIGHT_AM, SUNDAY, FitnessType.Spin));
    addLesson(new Lesson("Y007", EIGHT_AM, SUNDAY, FitnessType.Yoga));
    addLesson(new Lesson("Z007", EIGHT_AM, SUNDAY, FitnessType.Zumba));

    addLesson(new Lesson("A008", FIVE_PM, SUNDAY, FitnessType.Aquacise));
    addLesson(new Lesson("S008", FIVE_PM, SUNDAY, FitnessType.Spin));
    addLesson(new Lesson("Y008", FIVE_PM, SUNDAY, FitnessType.Yoga));
    addLesson(new Lesson("Z008", FIVE_PM, SUNDAY, FitnessType.Zumba));
    return this.lessons;
  }

  public void displayTimeTableHeading() {
    System.out.printf("\n%-15s%-20s%-20s%-20s%-15s", "Lesson Code","Time", "Day", "Lesson", "Cost");
  }

  public void displayTimeTableDetails(Lesson lesson) {
    System.out.printf("\n%-15s%-20s%-20s%-20s%-15s", lesson.getLessonCode(),lesson.getTime(), lesson.getDay(),
        lesson.getFitnessType(), lesson.getCost());
  }

  public void displayMonthlyReportHeading() {
    System.out.printf("\n%-15s%-20s%-15s", "Lesson Code","No. of Customers", "Rating Average");
  }

  public void displayMonthlyReportDetails(int size, String code, int rating) {
    System.out.printf("\n%-15s%-20s%-15s", code,size, rating);
  }

}
