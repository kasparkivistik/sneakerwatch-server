package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.service.SneakerSearchQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SneakerSearchQueryController {

    @Resource
    SneakerSearchQueryService sneakerSearchQueryService;

    @GetMapping("/queries/getAll")
    public @ResponseBody
    ResponseEntity<?> getAll() {
        return new ResponseEntity<>(sneakerSearchQueryService.getAll(), HttpStatus.OK);
    }
}
