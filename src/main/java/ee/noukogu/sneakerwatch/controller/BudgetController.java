package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.model.Budget;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/public/budget")
public class BudgetController {

    @GetMapping
    public @ResponseBody
    Budget getBudget() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new Budget();
    }
}
