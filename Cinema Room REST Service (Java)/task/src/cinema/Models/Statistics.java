package cinema.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {


    @JsonProperty("current_income")
    private int income;
    @JsonProperty("number_of_available_seats")
    private int numAvailableSeats;
    @JsonProperty("number_of_purchased_tickets")
    private int numPurchasedTickets;

    public Statistics() {
    }

    public Statistics(int income, int numAvailableSeats, int numPurchasedTickets) {
        this.income = income;
        this.numAvailableSeats = numAvailableSeats;
        this.numPurchasedTickets = numPurchasedTickets;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getNumAvailableSeats() {
        return numAvailableSeats;
    }

    public void setNumAvailableSeats(int numAvailableSeats) {
        this.numAvailableSeats = numAvailableSeats;
    }

    public int getNumPurchasedTickets() {
        return numPurchasedTickets;
    }

    public void setNumPurchasedTickets(int numPurchasedTickets) {
        this.numPurchasedTickets = numPurchasedTickets;
    }
}
