public class BasicContainer extends Container {
    public BasicContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        return 2.5 * getWeight();
    }

    @Override
    public String getContainerType() {
        return "Basic";
    }
}
