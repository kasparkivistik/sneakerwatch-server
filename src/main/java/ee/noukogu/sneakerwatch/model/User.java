package ee.noukogu.sneakerwatch.model;

import ee.noukogu.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user")
public class User {

    @Id
    private long id;
    private String email;
    private String givenName;
    private String surname;
    private Gender gender;
    private List<SneakerSearchQuery> previousQueries;
}
