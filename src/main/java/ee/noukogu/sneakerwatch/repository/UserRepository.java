package ee.noukogu.sneakerwatch.repository;

import ee.noukogu.sneakerwatch.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User, Long> {

    User getById(long id);
}
