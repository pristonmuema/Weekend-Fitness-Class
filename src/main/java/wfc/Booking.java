package wfc;

public class Booking {

  private String bookingId;
  private String lessonCode;
  private Rating rating;
  private String idNo;
  private FitnessType fitnessType;
  private String review;
  private double cost;

  private String date;
  private Status status;

  public Booking(String bookingId, String lessonCode, String idNo, FitnessType fitnessType,
      String date, Status status) {
    this.bookingId = bookingId;
    this.lessonCode = lessonCode;
    this.idNo = idNo;
    this.fitnessType = fitnessType;
    this.cost = getFitnessType().getCost();
    this.date = date;
    this.status = status;
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

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getBookingId() {
    return bookingId;
  }

  public String getDate() {
    return date;
  }
}
