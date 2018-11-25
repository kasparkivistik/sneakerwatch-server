package ee.noukogu.sneakerwatch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    private String address;
    private double distance;
    private String urlToGoogleMaps;

}
