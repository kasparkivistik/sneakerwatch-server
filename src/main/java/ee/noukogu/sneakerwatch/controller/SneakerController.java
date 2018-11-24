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

@CrossOrigin
@RestController
@RequestMapping("/api/public/sneakers")
public class SneakerController {

    @Resource
    SneakerService sneakerService;

    @GetMapping
    public @ResponseBody
    Page<Sneaker> getAll(Pageable pageable) {
        return sneakerService.getAll(pageable);
    }

    @PostMapping("/choose")
    public @ResponseBody
    ResponseEntity<?> chooseSneakers(@RequestBody SneakerSearchQuery sneakerSearchQuery) {
        return new ResponseEntity<>(sneakerService.chooseWithQuery(sneakerSearchQuery), HttpStatus.OK);
    }

    @GetMapping("/search")
    public @ResponseBody
    ResponseEntity<?> searchSneakers(@RequestBody SneakerSearchQuery sneakerSearchQuery, Pageable pageable) {
        return new ResponseEntity<>(sneakerService.searchWithQuery(sneakerSearchQuery, pageable), HttpStatus.OK);
    }

    @GetMapping("getByName/{name}")
    public Sneaker getSneakerByName(@PathVariable("name") String name) {
        return sneakerService.getByName(name);
    }

    @GetMapping("getById/{id}")
    public Sneaker getById(@PathVariable("id") long id) {
        return sneakerService.getById(id);
    }
}
