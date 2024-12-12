package RealTimeEventTicketingSystem;

import org.apache.log4j.Logger;

import java.util.Vector;

public class TicketPool {
    private int maximumTicketCapacity;
    private Vector<Ticket> ticketQueue;
    private static Logger logger = Logger.getLogger(TicketPool.class);


    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = new Vector<>();
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public void setMaximumTicketCapacity(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    /**
     * Adds a ticket to the pool. If the pool is full, the thread waits until space becomes available.
     *
     * @param ticket the ticket to add to the pool
     */
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maximumTicketCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
        ticketQueue.add(ticket);
        logger.info("Ticket added by - " + Thread.currentThread().getName() +" - current size is - " + ticketQueue.size());
        notifyAll();
    }

    /**
     * Buys a ticket from the pool. If the pool is empty, the thread waits until a ticket becomes available.
     *
     * @return the ticket bought from the pool
     */
    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error(e);
                throw new RuntimeException(e.getMessage());

            }
        }
        Ticket ticket = ticketQueue.remove(0);
        logger.info("Ticket bought by - " + Thread.currentThread().getName() +" - current size is - " + ticketQueue.size() + " - Ticket is - " + ticket);
        notifyAll();

        return ticket;
    }
}
