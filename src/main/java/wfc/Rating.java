package wfc;

public enum Rating {
  V_Dissatisfied(1, "Very dissatisfied"),
  Dissatisfied(2, "Dissatisfied"),
  Ok(3, "Ok"),
  Satisfied(4, "Satisfied"),
  V_Satisfied(5, "Very Satisfied");

  private int index;
  private String value;

  Rating(int index, String value) {
    this.index = index;
    this.value = value;
  }

  public int getIndex() {
    return index;
  }

  public String getValue() {
    return value;
  }
  public static Rating toRating( int rate) {
    switch (rate) {
      case 1:
        return V_Dissatisfied;
      case 2:
        return Dissatisfied;
      case 3:
        return Ok;
      case 4:
        return Satisfied;
      case 5:
        return V_Satisfied;
    }
    return Rating.Ok;
  }
}
