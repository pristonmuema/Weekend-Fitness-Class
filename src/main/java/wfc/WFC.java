package wfc;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WFC {

  static Scanner sc = new Scanner(System.in);
  static Customer theCustomer;
  static IService service = new Service(new Repository());
  static List<Lesson> lessonList = service.getLessons();

  public static void handleUser() {
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
          listAllCustomers();
          break;
        case 4:
          listAllBooking();
          break;
        case 5:
          monthlyReport();
          break;
        case 6:
          monthlyTypeReport();
          break;
        default:
          System.out.println("\n Error choice not supported");
      }
      pChoice = getMainMenu();
    }
  }

  private static void listAllBooking() {
    displayBookingHeader();
    for (Booking booking : service.listAllBookings()) {
      displayBookingDetails(booking);
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
    service.addCustomers(theCustomer);
    System.out.println("Registration successful");
    initialiseCustomer(name);
  }

  private static void logIn() {
    System.out.print("Enter customer id to log in.\n =>");
    String cName = sc.next();
    var optionalCustomer = service.getOptionalCustomer(cName);
    if (optionalCustomer.isEmpty()) {
      System.out.println("The customer does not exist");
      return;
    }
    theCustomer = optionalCustomer.get();
    initialiseCustomer(theCustomer.getName());
  }

  private static void listAllCustomers() {
    displayCustomerHeader();
    for (Customer customer : service.getCustomerList()) {
      displayCustomerDetails(customer);
    }
  }

  private static void monthlyTypeReport() {
    System.out.print("Enter Month e.g 03 \n =>");
    String month = sc.next();
    displayMonthlyTypeReportHeading();
    for (FitnessType fitnessType : FitnessType.values()) {
      AtomicInteger count = new AtomicInteger();
      for (Booking booking : service.listAllBookings()) {
        if (Objects.equals(fitnessType, booking.getFitnessType())
            && booking.getStatus() == Status.Attended && Objects.equals(booking.getDate(), month)) {
          count.addAndGet((int) fitnessType.getCost());
        }
      }

      displayMonthlyTypeReportDetails(fitnessType.name(), count);
    }
  }

  private static void monthlyReport() {
    System.out.print("Enter Month e.g 03 \n =>");
    String month = sc.next();
    displayMonthlyReportHeading();
    for (Lesson lesson : lessonList.stream().collect(collectingAndThen(toCollection(()-> new TreeSet<>(
        Comparator.comparing(Lesson::getLessonCode))), ArrayList::new))) {
      AtomicInteger atomicInteger = new AtomicInteger();
      AtomicInteger customerCount = new AtomicInteger();
      for (Booking booking : service.listAllBookings()) {
        if (Objects.equals(lesson.getLessonCode(), booking.getLessonCode())
            && booking.getStatus() == Status.Attended && Objects.equals(booking.getDate(), month)) {
          atomicInteger.addAndGet(booking.getRating().getIndex());
          customerCount.incrementAndGet();
        }
      }
      int ratingAvg = 0;
      if (atomicInteger.get() != 0 && customerCount.get() != 0) {
        ratingAvg = atomicInteger.get() / customerCount.get();
      }
      displayMonthlyReportDetails(customerCount.get(), lesson.getLessonCode(),
          ratingAvg);
    }

  }

  private static void initialiseCustomer(String name) {
    int choice = getCustomerMenu(name);
    while (choice != 0) {
      switch (choice) {
        case 1:
          viewTimeTable();
          break;
        case 2:
          listBookedLessons();
          break;
        case 3:
          bookLesson();
          break;
        case 4:
          cancelBooking();
          break;
        case 5:
          attendLesson();
          break;
        case 6:
          changeLesson();
          break;
        case 7:
          handleUser();
          break;
        default:
          System.out.println("\n Error, choice not recognised");
      }
      choice = getCustomerMenu(name);
    }
  }

  private static void listBookedLessons() {
    displayBookingHeader();
    var bookings = service.listAllBookings().stream().filter(booking -> Objects.equals(booking.getIdNo(),
        theCustomer.getIdNo())).collect(
        Collectors.toList());
    for (Booking booking: bookings) {
      displayBookingDetails(booking);
    }

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

  private static void bookLesson() {
    System.out.println("Enter Lesson Code");
    String lessonCode = sc.next();
    System.out.println("Enter Month e.g 03 for Match");
    String date = sc.next();
    addBooking(lessonCode, date);
  }

  private static void cancelBooking() {
    System.out.println("Enter Lesson Code you want to cancel");
    String lessonCode = sc.next();
    removeBooking(lessonCode);
  }

  private static void changeLesson() {
    System.out.println("\n Enter Lesson Code you want to change");
    String oldCode = sc.next();
    System.out.println("\n Enter the new Lesson Code");
    String newCode = sc.next();
    System.out.println("\n Enter Month e.g 03 for April");
    String date = sc.next();
    modifyBooking(oldCode, newCode, date);
  }

  public static void modifyBooking(String oldCode, String newCode, String date) {
    // Remove the old Lesson
    removeBooking(oldCode);
    // Add the new Lesson
    addBooking(newCode, date);
  }

  private static void attendLesson() {
    System.out.println("\n Enter Lesson Code you want to attend");
    String lessonCode = sc.next();
    attendLessons(lessonCode);

    System.out.println(" \n The Lesson is now Ending please, rate the lesson");
    for (Rating rating : Rating.values()) {
      System.out.println(rating.getIndex() + " " + rating.getValue());
    }

    System.out.println(" \n Enter your rating 1 to 5");
    int score = sc.nextInt();
    String review;
    review = getReviewFromUser();
    while (review.equals("")) {
      review = getReviewFromUser();
    }

    var customerBooking = service.getOptionalBooking(lessonCode, theCustomer.getIdNo());
    String finalReview = review;
    customerBooking.ifPresent(booking ->
    {
      booking.setRating(Rating.toRating(score));
      booking.setStatus(Status.Attended);
      booking.setReview(finalReview);
    });
  }

  private static String getReviewFromUser() {

    System.out.print("\nPlease enter your review ==> ");

    return sc.nextLine();
  }


  private static void addBooking(String lessonCode, String date) {
    var checkExist = service.getOptionalBooking(lessonCode, theCustomer.getIdNo());
    if (checkExist.isPresent()) {
      System.out.println("The Lesson is already booked by the customer");
      return;
    }
    var optionalLesson = service.getOptionalLesson(lessonCode);
    if (optionalLesson.isEmpty()) {
      System.out.println("The lesson does not exists");
      return;
    }
    var lesson = optionalLesson.get();
    if (lesson.isFull()) {
      System.out.println("Lesson is fully booked");
      return;
    }
    lesson.addCustomer(theCustomer);
    theCustomer.addLesson(lesson);
    service.listAllBookings().add(
        new Booking(UUID.randomUUID().toString(), lessonCode, theCustomer.getIdNo(), lesson.getFitnessType(),
            date, Status.Booked));
    System.out.println("Successfully Booked the Lesson");
  }

  private static void removeBooking(String lessonCode) {
    var optionalLesson = service.getOptionalLesson(lessonCode);
    if (optionalLesson.isEmpty()) {
      System.out.println("This lesson does not exist");
      return;
    }
    var lesson = optionalLesson.get();
    var customerBooking = service.getOptionalBooking(lessonCode, theCustomer.getIdNo());
    if (customerBooking.isEmpty()) {
      System.out.println("The customer has not yet booked this lesson");
      return;
    }
    if (lesson.isAttend()) {
      System.out.println("The lesson has been attended");
      return;
    }
    lesson.removeCustomer(theCustomer);
    customerBooking.get().setStatus(Status.Cancelled);
    theCustomer.removeLesson(lesson);
    System.out.println("Successfully Canceled the booking");

  }

  static Optional<Lesson> getOptionalBookedLessonByCustomer(String lessonCode) {
    return theCustomer.getLessons().stream().filter(less -> Objects.equals(
        less.getLessonCode(), lessonCode)).findFirst();
  }

  private static void attendLessons(String lessonCode) {
    var customerLesson = getOptionalBookedLessonByCustomer(lessonCode);
    if (customerLesson.isPresent()) {
      customerLesson.get().setAttend(true);
    } else {
      System.out.println("The lesson is not booked by the customer");

    }
  }

  private static void getTimetableByFitnessType(String fitnessType) {
    displayTimeTableHeading();
    var dayLessons = service.getTimetableByFitnessType(fitnessType);
    for (Lesson lesson : dayLessons) {
      displayTimeTableDetails(lesson);
    }
  }

  private static void getTimetableByDay(String day) {
    displayTimeTableHeading();
    List<Lesson> dayLessons = service.getTimetableByDay(day);
    for (Lesson lesson : dayLessons) {
      displayTimeTableDetails(lesson);
    }
  }

  private static int getCustomerMenu(String name) {
    System.out.println("\n " + name + " Choose an activity by entering its number. \n");
    String[] activities = {"1.View time table", "2.List booked Lessons", "3.Book Lesson", "4.Cancel booking",
        "5.Attend Lesson", "6.Change Lesson", "7.Back"};

    for (String s : activities) {
      System.out.println(s);
    }
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

  private static void viewTimeTableByFitnessType() {
    System.out.println("\n Choose the type: Yoga, Aquacise,Zumba or Spin \n Enter choice==>");
    String type = sc.next();
    getTimetableByFitnessType(type);
  }

  private static void viewTimeTableByDay() {
    System.out.println("\n Choose the weekendDay, Saturday or Sunday \n Enter choice==>");
    String day = sc.next();
    getTimetableByDay(day);
  }

  private static int getMainMenu() {
    System.out.print("\n Pick action.  \n 1. Register customer. \n 2. Login customer. "
        + "\n 3. List all customers \n 4. List all Bookings \n 5. Generate Monthly Lesson Report."
        + " \n 6. Generate Monthly Type Based Report.");

    System.out.print("\nEnter choice==> ");
    return sc.nextInt();
  }

  public static void displayTimeTableHeading() {
    System.out.printf("\n%-15s%-20s%-20s%-20s%-15s", "Lesson Code", "Time", "Day", "Lesson",
        "Cost");
  }

  public static void displayTimeTableDetails(Lesson lesson) {
    System.out.printf("\n%-15s%-20s%-20s%-20s%-15s", lesson.getLessonCode(), lesson.getTime(),
        lesson.getWeekendDay(),
        lesson.getFitnessType(), lesson.getCost());
  }

  public static void displayMonthlyReportHeading() {
    System.out.printf("\n%-15s%-20s%-15s", "Lesson Code", "No. of Customers", "Rating Average");
  }

  public static void displayMonthlyReportDetails(int size, String code, int rating) {
    System.out.printf("\n%-15s%-20s%-15s", code, size, rating);
  }

  public static void displayCustomerDetails(Customer customer) {
    System.out.printf("\n%-15s%-20s%-20s%-20s%-15s", customer.idNo, customer.name, customer.phoneNo,
        customer.getAddress(), customer.lessons.size());
  }

  private static void displayBookingDetails(Booking booking) {
    String rating = null;
    if (booking.getRating() != null) {
      rating = booking.getRating().getValue();
    }
    System.out.printf("\n%-45s%-20s%-20s%-20s%-20s%-20s%-20s%-15s", booking.getBookingId(),
        booking.getDate(),
        booking.getStatus(),
        booking.getReview(), booking.getLessonCode(), booking.getCost(), booking.getIdNo(),
        rating);
  }

  public static void displayCustomerHeader() {
    System.out.printf("\n%-15s%-20s%-20s%-20s%-15s", "ID", "Name", "Phone No",
        "Address", "No. Of Lessons");
  }

  public static void displayBookingHeader() {
    System.out.printf("\n%-45s%-20s%-20s%-20s%-20s%-20s%-20s%-15s", "BookingId", "Date", "Status",
        "Review", "Lesson code", "Cost", "CustomerId", "Rating");
  }

  private static void displayMonthlyTypeReportHeading() {
    System.out.printf("\n%-15s%-15s", "Fitness Type", "Amount");
  }

  private static void displayMonthlyTypeReportDetails(String name, AtomicInteger count) {
    System.out.printf("\n%-15s%-15s", name, count.get());
  }

}
