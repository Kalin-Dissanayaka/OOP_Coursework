package RealTimeEventTicketingSystem;

public class Customer implements Runnable{
    private int customerId;
    private int ticketQuantity;
    private int customerRetrievalInterval;
    private TicketPool ticketPool;

    public Customer(int customerId, int ticketQuantity, int customerRetrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.ticketQuantity = ticketQuantity;
        this.customerRetrievalInterval = customerRetrievalInterval;
        this.ticketPool = ticketPool;
    }


    @Override
    public void run() {
        for (int i = 0; i < ticketQuantity; i++) {
            Ticket ticket = ticketPool.buyTicket();
            if (ticket != null) {
                System.out.println("Customer " + customerId + " purchased: " + ticket);
            } else {
                System.out.println("Customer " + customerId + " couldn't purchase a ticket.");
            }
        } try {
            Thread.sleep(customerRetrievalInterval * 3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Customer " + customerId + " interrupted.");
            }
        //System.out.println("Customer " + customerId + " finished purchasing tickets.");
    }

}
