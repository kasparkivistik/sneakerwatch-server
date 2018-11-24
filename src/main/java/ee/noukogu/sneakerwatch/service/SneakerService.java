package ee.noukogu.sneakerwatch.service;

import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.model.SneakerSearchQuery;
import ee.noukogu.sneakerwatch.repository.SneakerRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class SneakerService {

    @Resource
    SneakerRepository repository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    public Sneaker add(Sneaker sneaker) {
        return repository.save(sneaker);
    }

    public List<Sneaker> getAll() {
        List<Sneaker> sneakers = new ArrayList<>();
        Iterable<Sneaker> sneakerIterable = repository.findAll();
        sneakerIterable.forEach(sneakers::add);
        return sneakers;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Set<String> getBrands() {
//        List<String> brands = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSourceFilter(new FetchSourceFilter(new String[]{"brand"}, new String[]{}))
                .build();
        List<Sneaker> sneakers = elasticsearchTemplate.queryForList(searchQuery, Sneaker.class);
        return sneakers.stream().map(Sneaker::getBrand).collect(Collectors.toSet());
    }

    public List<Sneaker> searchWithQuery(SneakerSearchQuery sneakerSearchQuery) {
       return new ArrayList<>();
    }
}
