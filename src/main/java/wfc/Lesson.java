package wfc;

import java.util.ArrayList;
import java.util.List;

public class Lesson {

  private static final int LESSON_CAPACITY = 5;
  String lessonCode;
  String time;
  String weekendDay;
  FitnessType fitnessType;
  List<Customer> customerList = new ArrayList<>();
  boolean isAttend;

  double cost;

  public Lesson(String lessonCode, String time, String weekendDay,
      FitnessType fitnessType) {
    this.lessonCode = lessonCode;
    this.time = time;
    this.weekendDay = weekendDay;
    this.fitnessType = fitnessType;
    this.cost = fitnessType.getCost();
  }

  public String getLessonCode() {
    return lessonCode;
  }

  public String getTime() {
    return time;
  }


  public String getWeekendDay() {
    return weekendDay;
  }


  public FitnessType getFitnessType() {
    return fitnessType;
  }

  public void addCustomer(Customer customer) {
    this.customerList.add(customer);
  }

  public void removeCustomer(Customer customer) {
    this.customerList.remove(customer);
  }

  public boolean isFull() {
    return this.customerList.size() == LESSON_CAPACITY;
  }

  public List<Customer> getCustomerList() {
    return customerList;
  }

  public double getCost() {
    return cost;
  }

  public boolean isAttend() {
    return isAttend;
  }

  public void setAttend(boolean attend) {
    isAttend = attend;
  }

}
