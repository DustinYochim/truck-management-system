public abstract class Container {
    private final int ID;
    private final int weight;

    Container(int ID, int weight) {
        this.ID = ID;
        this.weight = weight;
        System.out.printf("%s Container %d Created\n  Weight: %d\n\n", this.getContainerType(), this.getID(), this.getWeight());
    }

    public abstract double consumption();

    public int getID() {return ID;}
    public int getWeight() {return weight;}
    boolean equals(Container other) {
        return ID == other.getID() && weight == other.getWeight();
    }

    public abstract String getContainerType();

    public String toString() {
        return String.format("{%s: %d}", getContainerType(), getID());
    }
}
