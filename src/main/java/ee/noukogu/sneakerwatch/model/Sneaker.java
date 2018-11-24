package ee.noukogu.sneakerwatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Document(indexName = "sneaker", type = "sneaker")
public class Sneaker {

    @JsonProperty("name")
    @Id
    public String name;
    @JsonProperty("top")
    public String top;
    @JsonProperty("inspired")
    public String inspired;
    @JsonProperty("collection")
    public String collection;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("description")
    public String description;
    @JsonProperty("brand")
    public String brand;
    @JsonProperty("history")
    public String history;
    @JsonProperty("feature")
    public String feature;
    @JsonProperty("style")
    public String style;
    @JsonProperty("additional")
    public String additional;
    @JsonProperty("imageUrl")
    public String imageUrl;
    @JsonProperty("score")
    public Integer score;
    @JsonProperty("good")
    public List<String> good = null;
    @JsonProperty("bad")
    public List<String> bad = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
    @JsonIgnore
    private String special;
    @JsonIgnore
    private String collaboration;

}