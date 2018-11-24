package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.service.SneakerSearchQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/api/public/queries")
public class SneakerSearchQueryController {

    @Resource
    SneakerSearchQueryService sneakerSearchQueryService;

    @GetMapping
    public @ResponseBody
    ResponseEntity<?> getAll() {
        return new ResponseEntity<>(sneakerSearchQueryService.getAll(), HttpStatus.OK);
    }
}
