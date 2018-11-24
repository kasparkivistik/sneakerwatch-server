package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.service.FilterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/api/public/filter")
public class FilterController {

    @Resource
    private FilterService filterService;

    @GetMapping("/brands")
    public @ResponseBody
    ResponseEntity<?> getBrands() {
        return new ResponseEntity<>(filterService.getBrands(), HttpStatus.OK);
    }

    @GetMapping("/inspirations")
    public @ResponseBody
    ResponseEntity<?> getInspirations() {
        return new ResponseEntity<>(filterService.getInspirations(), HttpStatus.OK);
    }

}
