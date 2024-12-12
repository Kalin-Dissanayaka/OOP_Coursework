package RealTimeEventTicketingSystem;

import java.math.BigDecimal;

public class Vendor implements Runnable {
        private String vendorId;
        private int totalTickets;
        private int ticketReleaseRate;
        private TicketPool ticketPool;



        public Vendor(String vendorId, int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
            this.vendorId = vendorId;
            this.totalTickets = totalTickets;
            this.ticketReleaseRate = ticketReleaseRate;
            this.ticketPool = ticketPool;
        }

        public String getVendorId() {
            return vendorId;
        }

        public void setVendorId(String vendorId) {
            this.vendorId = vendorId;
        }

        public int getTotalTickets() {
            return totalTickets;
        }
        public void setTotalTickets(int totalTickets) {
            this.totalTickets = totalTickets;
        }

        public int getTicketReleaseRate() {
            return ticketReleaseRate;
        }
        public void setTicketReleaseRate(int ticketReleaseRate) {
            this.ticketReleaseRate = ticketReleaseRate;
        }

        public TicketPool getTicketPool() {
                return ticketPool;
        }
        public void setTicketPool(TicketPool ticketPool) {
            this.ticketPool = ticketPool;
        }

    /**
     * Executes the ticket releasing process. Tickets are released one at a time
     * into the shared ticket pool at the specified rate until all tickets are released.
     */
        @Override
        public void run() {
            int ticketNumber = 1;
            while (ticketNumber <= totalTickets) {
                Ticket ticket = new Ticket(ticketNumber++, "Event - " + ticketNumber, new BigDecimal("1000.00"));
                ticketPool.addTicket(ticket);
            }
            try {
                Thread.sleep(ticketReleaseRate * 3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                //System.out.println("Vendor interrupted.");
            }
            //System.out.println("Vendor " + vendorId + " finished releasing tickets.");
        }
    }

