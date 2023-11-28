# Container Trucks Management System

## Introduction
This project implements a simulation of a Truck management system using Java in IntelliJ. The goal is to analyze requests provided in input and perform the necessary actions. Container trucks move between terminals, carrying various types of containers and goods such as basic, heavy, refrigerator trucks, and tankers. The trucks require fuel to travel between terminals.

## Class Hierarchy and Details

### Interfaces
#### ITruck.java
```java
public interface ITruck {
    boolean goTo(Terminal p);
    void reFuel(double newFuel);
    boolean load(Container cont);
    boolean unLoad(Container cont);
}
```
#### ITerminal.java
```java
public interface ITerminal {
    void incomingTrucks(Truck T);
    void outgoingTruck(Truck T);
}
```

### Classes Implemented
1. **Main.java**
   - Main method to read inputs, make menu selections, and test the code.

2. **Terminal.java**
   - Variables:
     - int ID
     - double X
     - double Y
     - ArrayList<Container> containers
     - ArrayList<Truck> history
     - ArrayList<Truck> current
   - Methods:
     - Constructor with parameters ID, X, and Y.
     - double getDistance(Terminal other)

3. **Truck.java**
   - Variables:
     - int ID
     - double fuel
     - Terminal currentTerminal
   - Methods:
     - `public Truck(int ID, Terminal p, int totalloadCapacity, double fuelConsumptionPerKM...)`
     - ArrayList<Container> getCurrentContainers()

4. **Container.java**
   - Abstract class with fields:
     - int ID
     - int weight
   - Methods:
     - Constructor with parameters ID, weight
     - double consumption()
     - boolean equals(Container other)

5. **Basic.java** and **Heavy.java**
   - Extend Container class with a constructor similar to Container.
   - Fuel consumption:
     - Basic: 2.50 per unit of weight
     - Heavy: 3.00 per unit of weight

6. **RefrigeratorTruck.java** and **Tanker.java**
   - Extend Heavy class with their constructors.
   - Fuel consumption:
     - Refrigerator truck: 5.00 per unit of weight
     - Tanker: 4.00 per unit of weight

## Input/Output

### Input
The actions are specified in their customized format, with each line representing an event in the Terminal management system.

Actions:
1. Creating a container
2. Creating a Truck
3. Creating a Terminal
4. Loading a container to a Truck
5. Unloading a container from a Truck
6. Truck goes to another Terminal
7. Truck is refueled

### Output
Print the Trucks in the Terminals list in the main class with their attributes. Double values should have 2 digits after the decimal point. The output format should be ordered in ascending order of Terminal IDs, and within each Terminal, the Trucks should be ordered according to their IDs.
