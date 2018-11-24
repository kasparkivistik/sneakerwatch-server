package ee.noukogu.sneakerwatch.model;

import ee.noukogu.enums.Top;
import lombok.Data;

import java.util.Set;

@Data
public class SneakerSearchParams {

    Set<Top> tops;
    Budget budget;
    Set<String> brands;
    Set<String> sport;
    Set<String> inspired;

}
