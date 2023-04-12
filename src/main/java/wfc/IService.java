package wfc;

import java.util.List;
import java.util.Optional;

public interface IService {
  void addCustomers(Customer customer);
  Optional<Lesson> getOptionalLesson(String lessonCode);
  List<Lesson> getTimetableByFitnessType(String fitnessType);
  List<Lesson> getTimetableByDay(String day);
  List<Lesson> getLessons();
  List<Customer> getCustomerList();

  Optional<Customer> getOptionalCustomer(String id);
  Optional<Booking> getOptionalBooking(String lessonCode, String customerIdNo);
  List<Booking> listAllBookings();


}
