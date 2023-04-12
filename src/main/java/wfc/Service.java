package wfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Service implements IService {

  Repository repository;

  static List<Booking> bookings = new ArrayList<>();

  public Service(Repository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Lesson> getOptionalLesson(String lessonCode) {
    return repository.getTimeTable().stream()
        .filter(lesson -> Objects.equals(lesson.getLessonCode(), lessonCode))
        .findFirst();
  }

  @Override
  public List<Lesson> getTimetableByFitnessType(String fitnessType) {
    return repository.getTimeTable().stream()
        .filter(lesson -> Objects.equals(lesson.getFitnessType().toString(), fitnessType))
        .collect(Collectors.toList());
  }

  @Override
  public List<Lesson> getTimetableByDay(String day) {
    return repository.getTimeTable().stream()
        .filter(lesson -> Objects.equals(lesson.getWeekendDay(), day)).collect(Collectors.toList());
  }


  @Override
  public void addCustomers(Customer customer) {
    repository.addCustomers(customer);
  }

  @Override
  public List<Customer> getCustomerList() {
    return repository.getCustomerList();
  }

  @Override
  public Optional<Customer> getOptionalCustomer(String id) {
    return getCustomerList().stream().filter(customer -> Objects.equals(customer.getIdNo(), id))
        .findFirst();
  }

  @Override
  public Optional<Booking> getOptionalBooking(String lessonCode, String customerIdNo) {
    return bookings.stream().filter(booking -> Objects.equals(booking.getIdNo(), customerIdNo)
        && Objects.equals(booking.getLessonCode(), lessonCode)).findFirst();
  }

  @Override
  public List<Lesson> getLessons() {
    return repository.getTimeTable();
  }

  @Override
  public List<Booking> listAllBookings() {
    return bookings;
  }

}
