package ee.noukogu.sneakerwatch.service;

import ee.noukogu.enums.Top;
import ee.noukogu.sneakerwatch.model.Budget;
import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.model.SneakerSearchParams;
import ee.noukogu.sneakerwatch.model.SneakerSearchQuery;
import ee.noukogu.sneakerwatch.repository.SneakerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

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

        Budget budget = sneakerSearchQuery.getBudget();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
                .withFilter(rangeQuery("price")
                        .gte(budget.getStart())
                        .lte(budget.getEnd()))
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, Sneaker.class);
    }

    /*
        Set<Top> tops;
    Budget budget;
    Set<String> brands;
    Set<String> sport;
    Set<Purpose> inspired;
     */

    public List<Sneaker> getAllBySearchParams(SneakerSearchParams params, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().build();
        return elasticsearchTemplate.queryForList(searchQuery, Sneaker.class).stream()
                .filter(s -> params.getTops() == null || params.getTops().contains(Top.valueOf(s.getTop())))
                .filter(s -> params.getBudget() == null || params.getBudget().priceIsInBudget(s.getPrice()))
                .filter(s -> params.getBrands() == null || params.getBrands().contains(s.getBrand()))
                .filter(s -> params.getSport() == null || params.getSport().contains(s.getInspired()))
                .filter(s -> params.getInspired() == null || params.getInspired().contains(s.getInspired()))
                .collect(Collectors.toList());
        // return getPage(sneakers, pageable);
    }

    private Page<Sneaker> getPage(List<Sneaker> sneakers, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        List<Sneaker> pageSneakers = sneakers.subList(offset, Math.min(offset + pageSize, sneakers.size()));
        return new PageImpl<>(pageSneakers, pageable, sneakers.size());
    }

}
