package ee.noukogu.sneakerwatch.service;

import ee.noukogu.enums.Purpose;
import ee.noukogu.enums.Top;
import ee.noukogu.sneakerwatch.model.Budget;
import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.model.SneakerSearchParams;
import ee.noukogu.sneakerwatch.model.SneakerSearchQuery;
import ee.noukogu.sneakerwatch.repository.SneakerRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<String> getBrands() {
        List<Sneaker> sneakers = new ArrayList<>();
        repository.findAll().forEach(sneakers::add);
        return sneakers.stream().map(Sneaker::getBrand).collect(Collectors.toSet());
    }

    public List<Sneaker> searchWithQuery(SneakerSearchQuery sneakerSearchQuery) {

        Budget budget = sneakerSearchQuery.getBudget();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        String inspired = sneakerSearchQuery.getInspired().equals(Purpose.CASUAL) ? "Casual" : sneakerSearchQuery.getSport();
        MatchQueryBuilder inspiredQuery = matchQuery("inspired", inspired);
        MatchQueryBuilder topQuery = matchQuery("top", sneakerSearchQuery.getTop().getValue());
        List<String> brands = sneakerSearchQuery.getBrands();
        if (brands == null || brands.isEmpty()) {
            brands = new ArrayList<>(getBrands());
        }
        MatchQueryBuilder brandQuery = matchQuery("brand", brands).operator(Operator.OR);

        boolQueryBuilder.must(rangeQuery("price")
                .gte(budget.getStart())
                .lte(budget.getEnd()))
                .must(inspiredQuery)
                .must(topQuery)
                .must(brandQuery);


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withIndices("sneaker")
                .withPageable(new Pageable() {
                    @Override
                    public int getPageNumber() {
                        return 0;
                    }

                    @Override
                    public int getPageSize() {
                        return 300;
                    }

                    @Override
                    public long getOffset() {
                        return 0;
                    }

                    @Override
                    public Sort getSort() {
                        return null;
                    }

                    @Override
                    public Pageable next() {
                        return null;
                    }

                    @Override
                    public Pageable previousOrFirst() {
                        return null;
                    }

                    @Override
                    public Pageable first() {
                        return null;
                    }

                    @Override
                    public boolean hasPrevious() {
                        return false;
                    }
                })
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
