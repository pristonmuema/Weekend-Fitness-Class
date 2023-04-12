package wfc;

import java.util.ArrayList;
import java.util.List;

public class Customer {

  String idNo;
  String name;
  String phoneNo;
  String address;
  List<Lesson> lessons = new ArrayList<>();



  public Customer(String idNo, String name, String phoneNo, String address) {
    this.idNo = idNo;
    this.name = name;
    this.phoneNo = phoneNo;
    this.address = address;
  }

  public Customer() {

  }

  public String getIdNo() {
    return idNo;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public String getAddress() {
    return address;
  }

  public void addLesson(Lesson lesson) {
    this.lessons.add(lesson);
  }

  public void removeLesson(Lesson lesson) {
    this.lessons.remove(lesson);
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

}
