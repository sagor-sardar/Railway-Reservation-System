package entity;

public class Ticket {
    private String username;
    private String trainNo;
    private int quantity;

    public Ticket(String username, String trainNo, int quantity) {
        this.username = username;
        this.trainNo = trainNo;
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public int getQuantity() {
        return quantity;
    }
}