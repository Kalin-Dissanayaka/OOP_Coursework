package RealTimeEventTicketingSystem;

import java.math.BigDecimal;

public class Vendor implements Runnable {
        private int vendorId;
        private int totalTickets;
        private int ticketReleaseRate;
        private TicketPool ticketPool;

        public Vendor(int vendorId, int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
            this.vendorId = vendorId;
            this.totalTickets = totalTickets;
            this.ticketReleaseRate = ticketReleaseRate;
            this.ticketPool = ticketPool;
        }

        public int getVendorId() {
            return vendorId;
        }

        public void setVendorId(int vendorId) {
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


        @Override
        public void run() {
            int ticketNumber = 1;
            while (ticketNumber <= totalTickets) {
                Ticket ticket = new Ticket(ticketNumber++, "Event - " + ticketNumber, new BigDecimal("1000.00"));
                ticketPool.addTicket(ticket);
            }
            try {
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Vendor interrupted.");
            }
            System.out.println("Vendor " + vendorId + " finished releasing tickets.");
        }
    }

