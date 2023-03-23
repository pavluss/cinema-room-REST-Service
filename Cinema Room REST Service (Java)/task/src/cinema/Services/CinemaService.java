package cinema.Services;

import cinema.Models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaService {

    private Cinema cinema = new Cinema(9,9);
    private Map<UUID, Seat> ticketsDB = new HashMap();
    private int income = 0;

    public Cinema getAvailableSeats() {
        return cinema;
    }

    public ResponseEntity<String> purchaseSeat(Seat seat) {
        ResponseEntity response;
        int rowSeat = seat.getRow();
        int columnSeat = seat.getColumn();

        Optional<Seat> seatOptional = cinema.getAvailableSeats().stream()
                .filter(s -> s.getRow() == rowSeat && s.getColumn() == columnSeat)
                .findFirst();

        if (seatOptional.isEmpty()) {
            response = new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        } else if (seatOptional.get().isBooked()) {
            response = new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        } else {
            Ticket ticket = new Ticket(seatOptional.get());
            ticketsDB.put(ticket.getToken().getUuid(), ticket.getSeat());
            changeSeatAvailability(ticket.getSeat(), true);
            income += ticket.getSeat().getPrice();
            response = new ResponseEntity(ticket, HttpStatus.OK);
        }
        return response;
    }

    private synchronized void changeSeatAvailability(Seat seat, boolean booked) {
        cinema.getAvailableSeats().stream()
                .filter(s -> s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn())
                .forEach(s -> s.setBooked(booked));
    }

    public ResponseEntity<String> returnSeat(Token token) {
        ResponseEntity response;
        if (ticketsDB.containsKey(token.getUuid())) {
            Seat seat = ticketsDB.remove(token.getUuid());
            changeSeatAvailability(seat, false);
            income -= seat.getPrice();
            response = new ResponseEntity(Map.of("returned_ticket", seat), HttpStatus.OK);
        } else {
            response = new ResponseEntity(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public ResponseEntity<String> showStatistics(String password) {
        if ("super_secret".equals(password)) {
            int purchasedTickets = ticketsDB.size();
            int availableSeats = cinema.getTotalCount() - purchasedTickets;
            return new ResponseEntity(new Statistics(income, availableSeats, purchasedTickets), HttpStatus.OK);
        } else {
            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
    }
}
