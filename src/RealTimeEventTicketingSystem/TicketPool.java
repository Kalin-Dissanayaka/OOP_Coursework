package RealTimeEventTicketingSystem;

import java.util.Vector;

public class TicketPool {
    private int maximumTicketCapacity;
    private Vector<Ticket> ticketQueue;

    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = new Vector<>();
    }

    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maximumTicketCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ticketQueue.add(ticket);
        notifyAll();
        System.out.println("Ticket added by - " + Thread.currentThread().getName() +
                " - current size is - " + ticketQueue.size());
    }

    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Ticket ticket = ticketQueue.remove(0);
        notifyAll();
        System.out.println("Ticket bought by - " + Thread.currentThread().getName() +
                " - current size is - " + ticketQueue.size() + " - Ticket is - " + ticket);
        return ticket;
    }
}
