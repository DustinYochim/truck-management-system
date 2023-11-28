public class RefrigeratedContainer extends HeavyContainer {
    public RefrigeratedContainer(int contId, int weight) {
        super(contId, weight);
    }

    @Override
    public double consumption() {
        return 5.0 * getWeight();
    }

    @Override
    public String getContainerType() {
        return "Refrigerator Truck";
    }
}
