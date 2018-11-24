package ee.noukogu.sneakerwatch.model;

import ee.noukogu.enums.Purpose;
import ee.noukogu.enums.Top;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(indexName = "searchQuery")
public class SneakerSearchQuery {

//    private Gender gender;
    private Purpose inspired;
    private String sport;
    private Top top;
    private Budget budget;
    private List<String> brands;
}
