package ee.noukogu.sneakerwatch.service;

import ee.noukogu.enums.Purpose;
import ee.noukogu.sneakerwatch.model.Budget;
import ee.noukogu.sneakerwatch.model.PageableImpl;
import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.model.SneakerSearchQuery;
import ee.noukogu.sneakerwatch.repository.SneakerRepository;
import ee.noukogu.sneakerwatch.repository.SneakerSearchQueryRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class SneakerService {

    @Resource
    SneakerRepository repository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private FilterService filterService;
    @Resource
    private SneakerSearchQueryRepository sneakerSearchQueryRepository;

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

    public List<Sneaker> chooseWithQuery(SneakerSearchQuery sneakerSearchQuery) {

        //TODO: save with user pointer
        sneakerSearchQueryRepository.save(sneakerSearchQuery);

        List<Sneaker> sneakers = getAllWithQuery(sneakerSearchQuery);
//        sneakers = sneakers.size() < 4 ? getAll(pageable).getContent() : sneakers;
        if (sneakers.size() < 4) {

            SneakerSearchQuery otherSneakers = new SneakerSearchQuery();
            otherSneakers.setInspired(sneakerSearchQuery.getInspired());
            otherSneakers.setTop(sneakerSearchQuery.getTop());
            otherSneakers.setBudget(sneakerSearchQuery.getBudget());
            otherSneakers.setSport(sneakerSearchQuery.getSport());

            ArrayList<Sneaker> randomOtherSneakers = new ArrayList<>(getAllWithQuery(otherSneakers));
            Collections.shuffle(randomOtherSneakers);

            ArrayList<Sneaker> mutable = new ArrayList<>(sneakers);
            mutable.addAll(randomOtherSneakers);

            sneakers = mutable;
        }
        return sneakers.stream().limit(4).collect(toList());
    }

    public List<Sneaker> searchWithQuery(SneakerSearchQuery sneakerSearchQuery) {

        BoolQueryBuilder boolQueryBuilder = getBoolQueryBuilder(sneakerSearchQuery);
        Sort sort = Sort.by(Sort.Direction.DESC, "score");
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withIndices("sneaker")
                .withPageable(PageableImpl.builder().pageSize(9).pageNumber(0).offset(0).sort(sort).build())
                .build();

        return elasticsearchTemplate.queryForList(searchQuery, Sneaker.class);
    }


    private BoolQueryBuilder getBoolQueryBuilder(SneakerSearchQuery sneakerSearchQuery) {
        Budget budget = sneakerSearchQuery.getBudget();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        String inspired = sneakerSearchQuery.getInspired() == null ? null : sneakerSearchQuery.getInspired().equals(Purpose.CASUAL) ? "Casual" : sneakerSearchQuery.getSport();
        QueryBuilder inspiredQuery = inspired == null ? matchAllQuery() : matchQuery("inspired", inspired);
        QueryBuilder topQuery = sneakerSearchQuery.getTop() == null ? matchAllQuery() : matchQuery("top", sneakerSearchQuery.getTop().getValue());
        List<String> brands = sneakerSearchQuery.getBrands();
        if (brands == null || brands.isEmpty()) {
            brands = new ArrayList<>(filterService.getBrands());
        }
        MatchQueryBuilder brandQuery = matchQuery("brand", brands).operator(Operator.OR);

        boolQueryBuilder.must(rangeQuery("price")
                .gte(budget.getStart())
                .lte(budget.getEnd()))
                .must(inspiredQuery)
                .must(topQuery)
                .must(brandQuery);
        return boolQueryBuilder;
    }

    public Sneaker getById(long id) {
        return repository.findByPointer(id);
    }

    public Sneaker getByName(String name) {
        return repository.findByName(name);
    }

    public List<Sneaker> getAllWithQuery(SneakerSearchQuery sneakerSearchQuery) {

        //TODO: save with user pointer

        BoolQueryBuilder boolQueryBuilder = getBoolQueryBuilder(sneakerSearchQuery);
        Sort sort = Sort.by(Sort.Direction.DESC, "score");
        PageableImpl pageable = PageableImpl.builder().pageSize(1500).pageNumber(0).offset(0).sort(sort).build();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withIndices("sneaker")
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, Sneaker.class);
    }
}
