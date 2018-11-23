package ee.noukogu.sneakerwatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@Document(indexName = "sneaker")
public class Sneaker {

    @Id
    String name;
    String top;
    String inspired;
    String collection;
    Integer price;
    String description;
    String brand;
    String history;
    String feature;
    String style;
    String additional;
    String imageUrl;
    Integer score;
    List<String> good = null;
    List<String> bad = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();


}