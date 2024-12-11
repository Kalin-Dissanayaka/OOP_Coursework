package RealTimeEventTicketingSystem;

import org.apache.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("Configuration Form");
        System.out.println("------------------");

        //  for get Configuration Input
        int totalTickets = intInput("Enter the Total Number of Tickets: ");
        int ticketReleaseRate = intInput("Enter the Ticket Release Rate (in seconds): ");
        int customerRetrievalRate = intInput("Enter the Customer Retrieval Rate (in seconds): ");
        int maximumTicketsCapacity = intInput("Enter Maximum Ticket Capacity: ");

        System.out.println("\n Entry point for Simulation :");

        System.out.println("\nCustomer Configuration:");
        System.out.println("------------------");
        System.out.println("Note this: Ensure that (Number of customers) * (Tickets per customer) = Total tickets to the simple simulation.");

        int numberOfCustomers, numberOfTicketsPerCustomer;
        while (true) {
            numberOfCustomers = intInput("Enter number of Customers: ");
            numberOfTicketsPerCustomer = intInput("Enter number of Tickets each Customer should buy: ");
            if (numberOfCustomers * numberOfTicketsPerCustomer != totalTickets) {
                System.out.println("Error type: Customers must buy all tickets to match the total tickets for smooth simulation.");
            } else {
                break;
            }
        }

        System.out.println("\nVendor Configuration:");
        System.out.println("----------------------");
        System.out.println("Note this: Ensure that (Number of Vendors) * (Tickets per release) * (Releases) = Total tickets.");

        int numVendors, numberoOfTicketsPerRelease, numOfReleases;
        while (true) {
            numVendors = intInput("Enter number of Vendors: ");
            numberoOfTicketsPerRelease = intInput("Enter the number of Tickets per single release: ");
            numOfReleases = intInput("Enter the number of Releases each Vendor makes: ");
            if (numVendors * numberoOfTicketsPerRelease * numOfReleases != totalTickets) {
                System.out.println("Error type: Vendors must release all tickets to match the total tickets.");
            } else {
                break;
            }
        }

        System.out.println("\nStart the Simulation:");
        System.out.println("------------------");

        while (true) {
            String start = stringInput("You want to Start System? (Yes/No): ");
            if (start.equalsIgnoreCase("yes")) {
                TicketConfiguration configuration = new TicketConfiguration(ticketReleaseRate, totalTickets, customerRetrievalRate, maximumTicketsCapacity);
                TicketConfiguration.saveFileConfig(configuration, "datafile.json");
                configuration = TicketConfiguration.loadFileConfig("datafile.json");

                TicketPool ticketPool = new TicketPool(maximumTicketsCapacity);

                Vendor[] vendors = new Vendor[numVendors];
                Thread vendorThread;
                for (int i = 0; i < numVendors; i++) {
                    vendors[i] = new Vendor("Vendor-" + (i + 1), numberoOfTicketsPerRelease * numOfReleases, ticketReleaseRate, ticketPool);
                    vendorThread = new Thread(vendors[i], "Vendor-" + (i + 1));
                    vendorThread.start();
                }

                Customer[] customers = new Customer[numberOfCustomers];
                Thread customerThread;
                for (int i = 0; i < numberOfCustomers; i++) {
                    customers[i] = new Customer(i + 1, numberOfTicketsPerCustomer, customerRetrievalRate, ticketPool);
                    customerThread = new Thread(customers[i], "Customer-" + (i + 1));
                    customerThread.start();
                }

                System.out.println("Simulation running...");
                System.out.println("---------------------");

                // Wait until thread finish
                for (Vendor vendor : vendors) {
                    try {
                        Thread vendorThreadObj = new Thread(vendor);
                        vendorThreadObj.join();
                    } catch (InterruptedException e) {
                        logger.error("Vendor thread interrupted: " + e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                }

                for (Customer customer : customers) {
                    try {
                        Thread customerThreadObj = new Thread(customer);
                        customerThreadObj.join();
                    } catch (InterruptedException e) {
                        logger.error("Customer thread interrupted: " + e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println("Simulation Ended properly!");
                break;
            } else if (start.equalsIgnoreCase("no")) {
                System.out.println("Exiting System!");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'Yes' or 'No'.");
            }
        }
    }

    public static int intInput(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                logger.warn("Invalid input: Please enter an integer value.");
                scanner.next();
            }
        }
    }

    public static String stringInput(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextLine();
            } catch (Exception e) {
                logger.warn("Invalid input: Please enter a valid string.");
            }
        }
    }
}
