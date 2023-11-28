public class TankerContainer extends HeavyContainer {
    public TankerContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        return 4.0 * getWeight();
    }

    public String getContainerType() {
        return "Tanker";
    }
}
