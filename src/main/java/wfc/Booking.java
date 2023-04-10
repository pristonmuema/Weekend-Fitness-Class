package wfc;

public class Booking {

  String lessonCode;
  Rating rating;
  String idNo;
  FitnessType fitnessType;
  double cost;

  public Booking(String lessonCode, String idNo, FitnessType fitnessType) {
    this.lessonCode = lessonCode;
    this.idNo = idNo;
    this.fitnessType = fitnessType;
    this.cost = getFitnessType().getCost();
  }

  public double getCost() {
    return cost;
  }

  public String getLessonCode() {
    return lessonCode;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public String getIdNo() {
    return idNo;
  }

  public FitnessType getFitnessType() {
    return fitnessType;
  }
}
