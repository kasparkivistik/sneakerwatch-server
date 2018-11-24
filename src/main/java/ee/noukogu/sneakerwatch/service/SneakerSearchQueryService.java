package ee.noukogu.sneakerwatch.service;

import ee.noukogu.sneakerwatch.model.SneakerSearchQuery;
import ee.noukogu.sneakerwatch.repository.SneakerSearchQueryRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SneakerSearchQueryService {

    @Resource
    private SneakerSearchQueryRepository sneakerSearchQueryRepository;

    public List<SneakerSearchQuery> getAll() {
        List<SneakerSearchQuery> sneakerSearchQueries = new ArrayList<>();
        sneakerSearchQueryRepository.findAll().forEach(sneakerSearchQueries::add);
        return sneakerSearchQueries;
    }
}
