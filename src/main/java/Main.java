import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import wfc.Booking;
import wfc.Customer;
import wfc.Lesson;
import wfc.Rating;

public class Main {

  static Scanner sc = new Scanner(System.in);
  static Repository repository = new Repository();
  static Customer theCustomer;

  static List<Customer> customerList = new ArrayList<>(repository.getCustomerList());

  static List<Lesson> lessonList = new ArrayList<>(repository.getTimeTable());

  static List<Booking> bookings = new ArrayList<>();

  static List<Rating> ratings = new ArrayList<>(List.of(Rating.values()));

  public static void main(String[] args) {
    System.out.println(" Welcome to Weekend Fitness Club ");
    handleUser();
  }

  private static void handleUser() {

    int pChoice = getMainMenu();
    while (pChoice != 0) {
      switch (pChoice) {
        case 1:
          registerCustomer();
          break;
        case 2:
          logIn();
          break;
        case 3:
          monthlyReport();
          break;
        case 4:
          monthlyTypeReport();
          break;
        default:
          System.out.println("\n Error choice not supported");
      }
      pChoice = getMainMenu();
    }
  }

  private static void logIn() {
    System.out.print("Enter customer name to log in.\n =>");
    String cName = sc.next();
    for (Customer customer : repository.getCustomerList()) {
      if (customer.getName().contains(cName)) {
        theCustomer = customer;
        initialiseCustomer(cName);
      }
    }
  }


  static void registerCustomer() {
    System.out.print("Enter Customer IdNo\n =>");
    String idNo = sc.next();
    System.out.print("Enter name\n =>");
    String name = sc.next();
    System.out.print("Enter phoneNo\n =>");
    String phoneNo = sc.next();

    System.out.print("Enter address \n =>");
    String address = sc.next();
    theCustomer = new Customer(idNo, name, phoneNo, address);
    repository.addCustomers(theCustomer);
    System.out.println("Registration successful");
    initialiseCustomer(name);
  }

  private static void initialiseCustomer(String name) {
    int choice = getCustomerMenu(name);
    while (choice != 0) {
      switch (choice) {
        case 1:
          viewTimeTable();
          break;
        case 2:
          bookLesson();
          break;
        case 3:
          cancelBooking();
          break;
        case 4:
          attendLesson();
          break;
        case 5:
          changeLesson();
          break;
        case 6:
          handleUser();
          break;
        default:
          System.out.println("\n Error, choice not recognised");
      }
      choice = getCustomerMenu(name);
    }
  }

  private static void changeLesson() {
    System.out.println("\n Enter Lesson Code you want to change");
    String oldCode = sc.next();
    System.out.println("\n Enter the new Lesson Code");
    String newCode = sc.next();
    modifyBooking(oldCode, newCode);
  }

  private static void modifyBooking(String oldCode, String newCode) {
    // Remove the old Lesson
    removeBooking(oldCode);
    // Add the new Lesson
    addBooking(newCode);
  }

  private static void monthlyTypeReport() {

  }

  private static void monthlyReport() {
    repository.displayMonthlyReportHeading();
    for (Lesson lesson : lessonList) {
      AtomicInteger atomicInteger = new AtomicInteger();
      AtomicInteger customerCount = new AtomicInteger();
      for (Booking booking : bookings) {
        if (Objects.equals(lesson.getLessonCode(), booking.getLessonCode())) {
          atomicInteger.addAndGet(booking.getRating().getIndex());
          customerCount.incrementAndGet();
        }
      }
      int ratingAvg = 0;
      if (atomicInteger.get() != 0 && customerCount.get() != 0 ) {
        ratingAvg = atomicInteger.get()/customerCount.get();
      }

      repository.displayMonthlyReportDetails(customerCount.get(), lesson.getLessonCode(),
          ratingAvg);
    }
  }

  private static void attendLesson() {
    System.out.println("Enter Lesson Code you want to attend");
    String lessonCode = sc.next();
    attendLessons(lessonCode);

    System.out.println(" \n The Lesson is now Ending please, rate the lesson");
    for (Rating rating : ratings) {
      System.out.println(rating.getIndex() + " " + rating.getValue());
    }
    System.out.println(" \n Enter your rating 1 to 5");
    int score = sc.nextInt();
    rate(lessonCode, Rating.toRating(score));
  }

  private static void rate(String lessonCode, Rating rating) {
    var customerBooking = getOptionalBooking(lessonCode, theCustomer.getIdNo());
    customerBooking.ifPresent(booking -> booking.setRating(rating));
  }

  private static Optional<Booking> getOptionalBooking(String lessonCode, String customerIdNo) {
    return bookings.stream().filter(booking -> Objects.equals(booking.getIdNo(), customerIdNo)
        && Objects.equals(booking.getLessonCode(), lessonCode)).findFirst();
  }

  private static void attendLessons(String lessonCode) {
    var customerLesson = getOptionalBookedLessonByCustomer(lessonCode);
    if (customerLesson.isPresent()) {
      customerLesson.get().setAttend(true);
      System.out.println("The Lesson in now in-progress");
    } else {
      System.out.println("The lesson is not booked by the customer");
    }

  }

  private static void cancelBooking() {
    System.out.println("Enter Lesson Code you want to cancel");
    String lessonCode = sc.next();
    removeBooking(lessonCode);
    bookings.stream().filter(
            booking -> Objects.equals(booking.getLessonCode(), lessonCode) && Objects.equals(
                booking.getIdNo(), theCustomer.getIdNo()))
        .findFirst().ifPresent(les -> bookings.remove(les));
  }

  private static void bookLesson() {
    System.out.println("Enter Lesson Code");
    String lessonCode = sc.next();
    addBooking(lessonCode);
  }

  private static void addBooking(String lessonCode) {
    var checkExist = getOptionalBooking(lessonCode, theCustomer.getIdNo());
    if (checkExist.isPresent()) {
      System.out.println("The Lesson is already booked by the customer");
      return;
    }
    var lesson = getOptionalLesson(lessonCode);

    if (lesson.isFull()) {
      System.out.println("Lesson is fully booked");
      return;
    }
    lesson.addCustomer(theCustomer);
    theCustomer.addLesson(lesson);
    bookings.add(new Booking(lessonCode, theCustomer.getIdNo(), lesson.getFitnessType()));
    System.out.println("Successfully Booked the Lesson");
  }

  static Optional<Lesson> getOptionalBookedLessonByCustomer(String lessonCode) {
    return theCustomer.getLessons().stream().filter(less -> Objects.equals(
        less.getLessonCode(), lessonCode)).findFirst();
  }

  private static void removeBooking(String lessonCode) {
    var lesson = getOptionalLesson(lessonCode);

    var customerBooking = getOptionalBooking(lessonCode, theCustomer.getIdNo());
    if (customerBooking.isEmpty()) {
      System.out.println("The customer has not yet booked this lesson");
      return;
    }
    if (lesson.isAttend()) {
      System.out.println("The lesson has been attended");
      return;
    }
    lesson.removeCustomer(theCustomer);
    theCustomer.removeLesson(lesson);
    bookings.remove(customerBooking.get());

    System.out.println("Successfully Canceled the booking");


  }


  private static void viewTimeTable() {
    int choice = getTimeTableMenu();
    while (choice != 0) {
      switch (choice) {
        case 1:
          viewTimeTableByDay();
          break;
        case 2:
          viewTimeTableByFitnessType();
          break;
        default:
          System.out.println(" \n Error, choice not recognised");
      }
      choice = getTimeTableMenu();
    }
  }

  private static void viewTimeTableByFitnessType() {
    System.out.println("\n Choose the type: Yoga, Aquacise,Zumba or Spin \n Enter choice==>");
    String type = sc.next();
    getTimetableByFitnessType(type);
  }

  private static void viewTimeTableByDay() {
    System.out.println("\n Choose the day, Saturday or Sunday \n Enter choice==>");
    String day = sc.next();
    getTimetableByDay(day);
  }

  private static int getMainMenu() {
    System.out.print("\n Pick action.  \n 1. Register customer. \n 2. Login customer. "
        + "\n 3. Generate Monthly Lesson Report. \n 4. Generate Monthly Type Based Report.");

    System.out.print("\nEnter choice==> ");
    return sc.nextInt();
  }

  private static int getTimeTableMenu() {
    System.out.println("\n Please select from the following");
    String[] activities = {"1.View by Day", "2.View by Fitness Type", "0.Back"};

    for (String s : activities) {
      System.out.println(s);
    }
    System.out.print("\n Enter choice==> ");
    return sc.nextInt();
  }

  private static int getCustomerMenu(String name) {
    System.out.println(name + " Choose an activity by entering its number.");
    String[] activities = {"1.View time table", "2.Book Lesson", "3.Cancel booking",
        "4.Attend Lesson", "5.Change Lesson", "6.Back"};

    for (String s : activities) {
      System.out.println(s);
    }
    System.out.print("\nEnter choice==> ");
    return sc.nextInt();
  }


  private static void getTimetableByDay(String day) {
    repository.displayTimeTableHeading();
    List<Lesson> dayLessons = lessonList.stream()
        .filter(lesson -> Objects.equals(lesson.getDay(), day)).collect(Collectors.toList());
    for (Lesson lesson : dayLessons) {
      repository.displayTimeTableDetails(lesson);
    }
  }

  private static void getTimetableByFitnessType(String fitnessType) {
    repository.displayTimeTableHeading();
    List<Lesson> dayLessons = lessonList.stream()
        .filter(lesson -> Objects.equals(lesson.getFitnessType().toString(), fitnessType))
        .collect(Collectors.toList());
    for (Lesson lesson : dayLessons) {
      repository.displayTimeTableDetails(lesson);
    }
  }

  private static Lesson getOptionalLesson(String lessonCode) {
    var optionalLesson = lessonList.stream()
        .filter(lesson -> Objects.equals(lesson.getLessonCode(), lessonCode))
        .findFirst();
    if (optionalLesson.isEmpty()) {
      System.out.println("The lesson does not exist");
    }
    return optionalLesson.get();

  }

  private static void endProgram() {
    System.out.print("          **Enter any letter to exit **.\n  =>");
    handleUser();
  }

}
