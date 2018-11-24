package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.model.Budget;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

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
