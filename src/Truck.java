import java.util.ArrayList;

public class Truck implements TruckInterface{
    private final int ID;
    private double fuel;
    private Terminal currentTerminal;
    private final int totalLoadCapacity;
    private int currentLoad = 0;
    private final double fuelConsumptionPerKM;

    private ArrayList<Container> containers;

    Truck(int ID, Terminal startingTerminal, int totalLoadCapacity, double fuelConsumptionPerKM) {
        this.ID = ID;
        this.currentTerminal = startingTerminal;
        this.totalLoadCapacity = totalLoadCapacity;
        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
        this.containers = new ArrayList<>();
        System.out.printf("Truck %d Created\n  Starting Terminal: %d\n  Load Capacity: %d\n  Fuel Consumption: %.2f\n\n", this.getID(), this.currentTerminal.getID(), this.totalLoadCapacity, this.fuelConsumptionPerKM);
    }
    @Override
    public boolean goTo(Terminal terminal) {
        double travelDistance = currentTerminal.getDistance(terminal);
        double requiredFuel = fuelConsumptionPerKM * travelDistance;
        if (requiredFuel <= fuel) {
            fuel -= requiredFuel;
            System.out.printf("Truck %d traveled from terminal %d to terminal %d. New fuel level: %.2f\n\n",
                    this.getID(), currentTerminal.getID(), terminal.getID(), this.fuel);
            currentTerminal = terminal;
            return true;
        } else {
            System.out.println("Not enough fuel to reach terminal.");
            return false;
        }

    }

    @Override
    public void refuel(double fuelToAdd) {
        if (fuelToAdd > 0) {
            System.out.printf("Truck: %d refueled...Starting fuel: %.2f, Current fuel %.2f\n\n", ID, fuel,
                    fuel + fuelToAdd);
            fuel += fuelToAdd;
        }
        else {
            // System.out.println("Invalid fuel amount. Fuel cannot be negative.");
            throw new IllegalArgumentException("Invalid fuel amount. Fuel cannot be negative.");
        }
    }

    @Override
    public boolean load(Container container) {
        if (container == null )
            throw new IllegalArgumentException("Container cannot be null.");
        if (currentLoad + container.getWeight() < totalLoadCapacity) {
            currentLoad += container.getWeight();
            containers.add(container);
            System.out.printf("Truck %d loaded with %s Container %d.\n  Initial Load: %d\n  New Load: %d\n\n",
                    this.getID(),
                    container.getContainerType(),
                    container.getID(),
                    this.currentLoad - container.getWeight(),
                    this.currentLoad);
            return true;
        } else {
            System.out.println("Failed to load container on truck. Maximum load reached.");
            return false;
        }
    }

    @Override
    public boolean unload(Container container) {
        if (containers.contains(container)) {
            currentLoad -= container.getWeight();
            containers.remove(container);
            System.out.printf("Container %d unloaded from Truck %d to Terminal %d. Trucks new load is %d.\n\n",
                    container.getID(), this.getID(), this.getCurrentTerminal().getID(), this.currentLoad);
            return true;
        } else {
            System.out.println("Failed to unload container.");
            return false;
        }
    }

    public int getID() {return ID;}
    public double getFuel() {return fuel;}
    public Terminal getCurrentTerminal() {return currentTerminal;}
    public int getTotalLoadCapacity() {return totalLoadCapacity;}
    public double getFuelConsumptionPerKM() {return fuelConsumptionPerKM;}

    public void setCurrentTerminal(Terminal terminal) {
        this.currentTerminal = terminal;
    }

    public ArrayList<Container> getContainers() {return containers;}

    public String toString() {
        return String.format("Truck %d: Fuel: %.2f, Containers: %s", this.getID(), this.getFuel(),
                this.getContainers());
    }

}
