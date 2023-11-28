import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final int CREATE_CONTAINER = 1;
    private static final int CREATE_TRUCK = 2;
    private static final int CREATE_TERMINAL = 3;
    private static final int LOAD_CONTAINER = 4;
    private static final int UNLOAD_CONTAINER = 5;
    private static final int SEND_TRUCK = 6;
    private static final int REFUEL_TRUCK = 7;

    public static void main(String[] args) {
        // Read input.
        if (args.length != 1) {
            System.out.println("Usage: java Main <input_file>");
            System.exit(1);
        }
        try {
            // Create a new Scanner object to read data from the input file with directory "args[0]".
            File inputFile = new File(args[0]);
            Scanner in = new Scanner(inputFile);

            // Create ArrayLists in order to reach any container, Truck or Terminal I want with IDs for indexes.
            ArrayList<Container> containers = new ArrayList<Container>();
            ArrayList<Truck> trucks = new ArrayList<Truck>();
            ArrayList<Terminal> terminals = new ArrayList<Terminal>();

            // Continue reading input until the end of the file
            while (in.hasNext()) {
                final int operation_type = in.nextInt();
                processOperations(operation_type, in, containers, trucks, terminals);
            }

            // Closes the Scanner
            in.close();

            // Output method calls
            printStatus(containers, trucks, terminals);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            throw new RuntimeException(e);
        }
    }

    private static void printStatus(ArrayList<Container> containers, ArrayList<Truck> trucks, ArrayList<Terminal> terminals) {
        for (Terminal currentTerminal : terminals) {
            System.out.println(currentTerminal.toString());
            System.out.println("  Containers: " + currentTerminal.getContainersByType());
            System.out.println("  Trucks:  " + currentTerminal.getCurrent());
        }
    }

    private static void processOperations(int operationType, Scanner in, ArrayList<Container> containers, ArrayList<Truck> trucks, ArrayList<Terminal> terminals) {
            switch (operationType) {
                case CREATE_CONTAINER: {
                    // Create a container.
                    final int cont_ID = containers.size();
                    final int port_ID = in.nextInt();
                    final int weight = in.nextInt();
                    Container cont;

                    // If the next input is an integer, the container is either type Heavy or Basic.
                    if (in.hasNext("[A-Za-z]")) {
                        // If the next input is a character, the container is either type Tanker or Refrigerated.
                        final char special_type = in.next().charAt(0);
                        if (special_type == 'T')
                            cont = new TankerContainer(cont_ID, weight);
                        else
                            cont = new RefrigeratedContainer(cont_ID, weight);
                    } else {
                        if (weight > 3000)
                            cont = new HeavyContainer(cont_ID, weight);
                        else
                            cont = new BasicContainer(cont_ID, weight);
                    }
                    // Add the generated container to the Terminals "containers" ArrayList and the general "containers" ArrayList.
                    Terminal terminal = terminals.get(port_ID);
                    terminal.getContainers().add(cont);
                    containers.add(cont);
                    if (in.hasNext()) {
                        in.nextLine();
                    }
                    break;
                }
                case CREATE_TRUCK: {
                    // Create a truck.
                    final int Truck_ID = trucks.size();
                    final int Terminal_ID = in.nextInt();
                    final int totalWeightCapacity = in.nextInt();
                    final double fuelConsumptionPerKM = in.nextDouble();
                    // Add the generated Truck to the Terminal's"current" ArrayList and the general "Trucks" ArrayList.
                    Terminal terminal = terminals.get(Terminal_ID);
                    Truck truck = new Truck(Truck_ID, terminal, totalWeightCapacity, fuelConsumptionPerKM);
                    terminal.incomingTruck(truck);
                    trucks.add(truck);
                    in.nextLine();
                    break;
                }
                case CREATE_TERMINAL: {
                    // Create a Terminal.
                    final int Terminal_ID = terminals.size();
                    final double X = in.nextDouble();
                    final double Y = in.nextDouble();
                    // Add the generated Terminal to the "terminals" ArrayList
                    terminals.add(new Terminal(Terminal_ID, X, Y));
                    in.nextLine();
                    break;
                }
                case LOAD_CONTAINER: {
                    // Load a container to a Truck.
                    final int Truck_ID = in.nextInt();
                    final int cont_ID = in.nextInt();
                    // if truck successfully loads
                    boolean result = trucks.get(Truck_ID).load(containers.get(cont_ID));
                    if (result) {
                        // Find terminal container is currently in and remove it
                        for (Terminal currentTerminal : terminals) {
                            Container currentContainer = containers.get(cont_ID);
                            currentTerminal.getContainers().remove(currentContainer);
                        }
                    }
                    break;
                }
                case UNLOAD_CONTAINER: {
                    // Unload a container from a Truck.
                    final int Truck_ID = in.nextInt();
                    final int cont_ID = in.nextInt();
                    boolean result = trucks.get(Truck_ID).unload(containers.get(cont_ID));
                    if (result) {
                        // Add container back to trucks current terminal
                        Terminal terminal = trucks.get(Truck_ID).getCurrentTerminal();
                        terminal.addContainer(containers.get(cont_ID));
                    }
                    break;
                }
                case SEND_TRUCK: {
                    // send Truck to another port.
                    final int Truck_ID = in.nextInt();
                    final int Terminal_ID = in.nextInt();
                    Truck truck = trucks.get(Truck_ID);
                    Terminal startingTerminal = truck.getCurrentTerminal();
                    Terminal endingTerminal = terminals.get(Terminal_ID);
                    startingTerminal.outgoingTruck(truck);
                    endingTerminal.incomingTruck(truck);
                    trucks.get(Truck_ID).goTo(terminals.get(Terminal_ID));
                    break;
                }
                case REFUEL_TRUCK: {
                    // Refuel Truck.
                    final int ID = in.nextInt();
                    final double fuel = in.nextDouble();
                    trucks.get(ID).refuel(fuel);
                    break;
                }
                default:
                    // Invalid operation.
                    System.out.printf("Invalid operation. %d\n", operationType);
            }
    }
}