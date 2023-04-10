package wfc;

public enum FitnessType {

  Spin(3000),
  Yoga(3500),
  Zumba(2500),
  Aquacise(2000);

  private final double cost;

  FitnessType(double cost) {
    this.cost = cost;
  }

  public double getCost() {
    return cost;
  }
}
