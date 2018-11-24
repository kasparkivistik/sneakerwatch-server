package ee.noukogu.sneakerwatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@Document(indexName = "searchQuery")
public class SneakerSearchQuery {
    private String sex;
    private String inspired;
    private String sport;
    private String top;
    private Budget budget;
}
