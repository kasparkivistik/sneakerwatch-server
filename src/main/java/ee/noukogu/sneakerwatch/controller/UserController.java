package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.model.User;
import ee.noukogu.sneakerwatch.service.GoogleFitService;
import ee.noukogu.sneakerwatch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/public/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private GoogleFitService googleFitService;

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        return new ResponseEntity<>(userService.add(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        User user = userService.getById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getGoogleFit")
    public ResponseEntity<?> getGoogleFitData() {
        return new ResponseEntity<>(googleFitService.getGoogleFitData(), HttpStatus.OK);
    }

}
