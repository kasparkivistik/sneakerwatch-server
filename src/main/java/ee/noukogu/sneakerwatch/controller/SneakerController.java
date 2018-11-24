package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.service.SneakerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class SneakerController {

    @Resource
    SneakerService sneakerService;

    @PostMapping("/sneaker/add")
    public ResponseEntity<?> add(@RequestBody Sneaker sneaker) {
        if (sneaker != null) {
            return new ResponseEntity<>(sneakerService.add(sneaker), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("My N-word, you didnt add a FUCKING SNEAKER", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sneaker/getAll")
    public @ResponseBody ResponseEntity<?> getAll() {
        return new ResponseEntity<>(sneakerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/sneaker/getBrands")
    public @ResponseBody ResponseEntity<?> getBrands() {
        return new ResponseEntity<>(sneakerService.getBrands(), HttpStatus.OK);
    }

    @DeleteMapping("/sneaker/deleteAll")
    public @ResponseBody ResponseEntity<?> deleteAll() {
        sneakerService.deleteAll();
        return new ResponseEntity<>("DELETED ALL SNEAKERS", HttpStatus.OK);
    }
}
