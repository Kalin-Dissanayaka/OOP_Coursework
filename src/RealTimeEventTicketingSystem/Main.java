package RealTimeEventTicketingSystem;

public class Main {
    public static void main(String[] args) {
        String fileName = "datafile.json";

        TicketConfiguration configures = new TicketConfiguration(10,    100, 5, 200);


        System.out.println("Save configurations.");
        TicketConfiguration.saveFile(configures, fileName);


        System.out.println("Load configuration.");
        TicketConfiguration loadConfiguration = TicketConfiguration.loadFile(fileName);
        System.out.println(loadConfiguration.toString());

        int maximumTicketsCapacity = 200;
        TicketPool ticketPool = new TicketPool(maximumTicketsCapacity);


        // Create Vendor thread
        Vendor vendor = new Vendor(1, 60, 2, ticketPool);
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
