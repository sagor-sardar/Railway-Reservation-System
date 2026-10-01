package entity;

public class Train {
    private String trainNo;
    private String name;
    private String from;
    private String to;
    private int totalSeats;
    private int ticketsSold;

    public Train(String trainNo, String name, String from, String to, int totalSeats) {
        this.trainNo = trainNo;
        this.name = name;
        this.from = from;
        this.to = to;
        this.totalSeats = totalSeats;
        this.ticketsSold = 0;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getName() {
        return name;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void sellTickets(int qty) {
        ticketsSold += qty;
    }

    public void refundTickets(int qty) {
        ticketsSold -= qty;
        if (ticketsSold < 0) ticketsSold = 0;
    }
}