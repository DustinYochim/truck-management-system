public class HeavyContainer extends Container{
    public HeavyContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        return 3.0 * getWeight();
    }

    @Override
    public String getContainerType() {
        return "Heavy";
    }
}
