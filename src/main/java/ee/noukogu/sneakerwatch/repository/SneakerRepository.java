package ee.noukogu.sneakerwatch.repository;

import ee.noukogu.sneakerwatch.model.Sneaker;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SneakerRepository extends ElasticsearchRepository<Sneaker, String> {

    Sneaker findByPointer(long id);

    Sneaker findByName(String name);
}
