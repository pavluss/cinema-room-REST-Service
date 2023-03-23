package cinema.Controllers;

import cinema.Models.Cinema;
import cinema.Models.Seat;
import cinema.Models.Token;
import cinema.Services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;


    @GetMapping("/seats")
    public Cinema getCinema() {
        return cinemaService.getAvailableSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseSeat(@RequestBody Seat seat) {
        return cinemaService.purchaseSeat(seat);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnSeat(@RequestBody Token token) {
        return cinemaService.returnSeat(token);
    }

    @PostMapping("/stats")
    public ResponseEntity<String> showStatistics(@RequestParam(required = false) String password) {
        return cinemaService.showStatistics(password);
    }

}
