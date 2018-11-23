package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.model.Budget;
import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.service.SneakerService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SneakerController {

    @Resource
    SneakerService service;

    @PostMapping("/sneaker/add")
    public ResponseEntity<?> add(@RequestBody Sneaker sneaker) {
        if (sneaker != null) {
            return new ResponseEntity<>(service.add(sneaker), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("My N-word, you didnt add a FUCKING SNEAKER", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sneaker/getAll")
    public ResponseEntity<?> getAll(Sneaker sneaker, Budget budget, SpringDataWebProperties.Pageable pageable) {
        return new ResponseEntity<>(service.getAll(sneaker, budget), HttpStatus.OK);
    }
}
