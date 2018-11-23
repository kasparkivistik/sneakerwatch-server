package ee.noukogu.sneakerwatch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SensorData {

    @Id
    Long personId;
}
