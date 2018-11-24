package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.model.SneakerSearchQuery;
import ee.noukogu.sneakerwatch.service.SneakerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client")
public class SneakerClientController {

    @Resource
    SneakerService sneakerService;

    @GetMapping("/sneaker/getAll")
    public @ResponseBody
    Page<Sneaker> getAll(Pageable pageable) {
        return sneakerService.getAll(pageable);
    }

    @PostMapping("sneaker/search")
    public @ResponseBody
    ResponseEntity<?> searchSneakers(@RequestBody SneakerSearchQuery sneakerSearchQuery) {
        return new ResponseEntity<>(sneakerService.searchWithQuery(sneakerSearchQuery), HttpStatus.OK);
    }
}
