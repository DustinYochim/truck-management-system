import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Terminal implements TerminalInterface {
    private final int ID;
    private final double X;
    private final double Y;
    private ArrayList<Container> containers = new ArrayList<>();
    private ArrayList<Truck> history = new ArrayList<>(); // keeps track of every truck that has visited
    private ArrayList<Truck> current = new ArrayList<>(); // keeps track of trucks currently at this terminal

    Terminal(int ID, double X, double Y) {
        this.ID = ID;
        this.X = X;
        this.Y = Y;
        System.out.printf("Terminal %d Created at (%.2f, %.2f)\n\n", this.getID(), this.getX(), this.getY());
    }

    public void incomingTruck(Truck truck) {
        if (!current.contains(truck)) {
            current.add(truck);
        }
    }
    public void outgoingTruck(Truck truck) {
        current.remove(truck);
        history.add(truck);
    }

    public int getID() {return ID;}

    public double getX() {return X;}

    public double getY() {return Y;}

    public double getDistance(Terminal other) {
        if (other == null) {
            throw new IllegalArgumentException("Parameter 'other' cannot be null.");
        }
        double dX = other.getX() - this.X;
        double dY = other.getY() - this.Y;
        return Math.sqrt(dX * dX + dY * dY);
    }

    public ArrayList<Truck> getCurrent() {
        return current;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public ArrayList<Container> getContainersByType() {
        ArrayList<Container> sortedContainers = new ArrayList<>(this.containers);
        Comparator<Container> typeComparator = Comparator.comparingInt(container -> {
            String type = container.getContainerType();
            return switch (type) {
                case "Basic" -> 1;
                case "Heavy" -> 2;
                case "Refrigerator Truck" -> 3;
                case "Tanker" -> 4;
                default -> Integer.MAX_VALUE;
            };
        });
        sortedContainers.sort(typeComparator);
        return sortedContainers;
    }

    public void addContainer(Container container) {
        this.containers.add(container);
    }

    @Override
    public String toString() {
        return String.format("Terminal %d: (%.2f,%.2f)", this.getID(), this.getX(), this.getY());
    }

}
