package ee.noukogu.sneakerwatch.service;

import ee.noukogu.sneakerwatch.model.Budget;
import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.model.SneakerSearchQuery;
import ee.noukogu.sneakerwatch.repository.SneakerRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class SneakerService {

    @Resource
    SneakerRepository repository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    public Sneaker add(Sneaker sneaker) {
        return repository.save(sneaker);
    }

    public Page<Sneaker> getAll(Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, Sneaker.class);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Sneaker> searchWithQuery(SneakerSearchQuery sneakerSearchQuery) {

        Budget budget = sneakerSearchQuery.getBudget();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        MatchQueryBuilder inspiredQuery = matchQuery("inspired", sneakerSearchQuery.getSport());
        MatchQueryBuilder topQuery = matchQuery("top", sneakerSearchQuery.getTop().getValue());
        boolQueryBuilder.must(rangeQuery("price")
                .gte(budget.getStart())
                .lte(budget.getEnd()))
                .must(inspiredQuery)
                .must(topQuery);


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
                .withQuery(boolQueryBuilder)
                .withIndices("sneaker")
                .build();

        return elasticsearchTemplate.queryForList(searchQuery, Sneaker.class).subList(0, 4);
    }

}
