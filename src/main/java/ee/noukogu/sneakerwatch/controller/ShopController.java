package ee.noukogu.sneakerwatch.controller;

import com.google.common.collect.ImmutableList;
import ee.noukogu.sneakerwatch.model.Shop;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/public")
public class ShopController {

    private static List<Shop> MOCK_SHOP_LIST = ImmutableList.of(
            new Shop("JD Sports", 1.3, "https://www.google.fi/maps/place/JD+Sports/@60.2103826,25.0780113,17z/data=!3m1!4b1!4m5!3m4!1s0x46920f5430650f71:0x2ef87cd6942e4696!8m2!3d60.21038!4d25.0802"),
            new Shop("Budget Sport", 3.6, "https://www.google.fi/maps/place/Budget+Sport/@60.2791772,24.9754724,15z/data=!4m5!3m4!1s0x0:0x42a9e3775b2f590f!8m2!3d60.2791772!4d24.9754724"),
            new Shop("Stadium", 6.9, "https://www.google.fi/maps/place/Stadium+Kamppi/@60.279284,24.8441463,11z/data=!3m1!5s0x46920a34a8b9d14d:0x9df3c32c363f1b70!4m8!1m2!2m1!1sstadium+helsinki!3m4!1s0x46920a34ab0b4145:0x6182506542bfb954!8m2!3d60.1686851!4d24.93203")
            );


    @GetMapping("/shops/{pointer}")
    public @ResponseBody
    ResponseEntity<?> getShops(@PathVariable Long pointer) {
        return new ResponseEntity<>(MOCK_SHOP_LIST, HttpStatus.OK);
    }

}
