package ee.noukogu.sneakerwatch.controller;

import ee.noukogu.sneakerwatch.model.Budget;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController("/api")
public class BudgetController {

    @GetMapping("budget")
    public @ResponseBody
    Budget getBudget() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new Budget();
    }
}
