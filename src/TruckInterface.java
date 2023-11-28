public interface TruckInterface {
    public boolean goTo(Terminal terminal);
    public void refuel(double newFuel);
    public boolean load(Container container);
    public boolean unload(Container container);

}
