package RealTimeEventTicketingSystem;

import java.util.Scanner;

import static RealTimeEventTicketingSystem.TicketConfiguration.saveFileConfig;

public class Main {
    public static void main(String[] args) {
        String fileName = "datafile.json";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of total tickets you want to buy: ");
        int totalTickets = scanner.nextInt();


        System.out.println("Enter the customer retrieve rate: ");
        int customerRetrievalRate = scanner.nextInt();


        System.out.println("Enter the ticket release rate ");
        int ticketReleaseRate = scanner.nextInt();


        System.out.println("Enter the maximum ticket capacity: ");
        int maximumTicketsCapacity = scanner.nextInt();

        TicketConfiguration configures = new TicketConfiguration(totalTickets,ticketReleaseRate, customerRetrievalRate, maximumTicketsCapacity);

        //System.out.println("Save configurations.");
        saveFileConfig(configures, fileName);


        //System.out.println("Load configuration.");
        TicketConfiguration.loadFileConfig(fileName);
        //System.out.println(loadConfiguration.toString());

        TicketPool ticketPool = new TicketPool(maximumTicketsCapacity);
        // Create Vendor thread
        Vendor vendor = new Vendor("kalin1", configures.getMaximumTicketsCapacity(), configures.getTicketReleaseRate(), ticketPool);
        Thread vendorThread = new Thread(vendor, "Vendor-1");
        vendorThread.start();

        Customer[] customers = new Customer[10]; // Creating array of customers
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(i +1, 6, 2, ticketPool);
            Thread customerThread = new Thread(customers[i], "Customer ID: " + (i+1));
            customerThread.start();
        }
    }
}
