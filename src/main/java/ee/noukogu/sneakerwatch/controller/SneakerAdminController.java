package ee.noukogu.sneakerwatch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.service.SneakerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/admin/sneakers")
public class SneakerAdminController {

    @Resource
    SneakerService sneakerService;


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Sneaker sneaker) {
        if (sneaker != null) {
            return new ResponseEntity<>(sneakerService.add(sneaker), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You didn't add a sneaker", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getFromJson")
    public ResponseEntity<?> populateDatabase(MultipartFile file) throws IOException {
        InputStream fileContent = file.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(fileContent));

        String json = in.lines().collect(Collectors.joining());
        ObjectMapper mapper = new ObjectMapper();

        List<Sneaker> sneakers = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Sneaker.class));
        long pointer = 0L;
        for (Sneaker sneaker : sneakers) {
            sneaker.setPointer(pointer++);
            sneakerService.add(sneaker);
        }
        return new ResponseEntity<>("Shoes added successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public @ResponseBody
    ResponseEntity<?> deleteAll() {
        sneakerService.deleteAll();
        return new ResponseEntity<>("DELETED ALL SNEAKERS", HttpStatus.OK);
    }

}
