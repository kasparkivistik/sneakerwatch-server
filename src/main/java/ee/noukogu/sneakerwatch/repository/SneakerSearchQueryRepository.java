package ee.noukogu.sneakerwatch.repository;

import ee.noukogu.sneakerwatch.model.SneakerSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SneakerSearchQueryRepository extends ElasticsearchRepository<SneakerSearchQuery, String> {
}
