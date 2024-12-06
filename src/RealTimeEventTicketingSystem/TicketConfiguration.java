package RealTimeEventTicketingSystem;

import java.io.*;
import com.google.gson.*;

public class TicketConfiguration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maximumTicketsCapacity;

    public TicketConfiguration(int ticketReleaseRate, int totalTickets, int customerRetrievalRate, int maximumTicketsCapacity) {
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maximumTicketsCapacity = maximumTicketsCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }
    public int getTotalTickets() {
        return totalTickets;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }
    public int getMaximumTicketsCapacity() {
        return maximumTicketsCapacity;
    }

    public void setMaximumTicketsCapacity(int maximumTicketsCapacity) {
        this.maximumTicketsCapacity = maximumTicketsCapacity;
    }
    @Override
    public String toString() {
        return "{\"totalTickets\":"+this.totalTickets+",\"ticketReleaseRate\":"+this.ticketReleaseRate+",\"customerRetrievalRate\":"+this.customerRetrievalRate+",\"maximumTicketsCapacity\":"+this.maximumTicketsCapacity+"}";
    }

    public static void saveFileConfig(TicketConfiguration configuration, String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new Gson();
            gson.toJson(configuration, writer);
            //System.out.println("Configuration successfully saved in to" + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TicketConfiguration loadFileConfig(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, TicketConfiguration.class);
        } catch (IOException e) {
            System.err.println("loading error configuration: " + e.getMessage());
        }
        return null;
    }
}
