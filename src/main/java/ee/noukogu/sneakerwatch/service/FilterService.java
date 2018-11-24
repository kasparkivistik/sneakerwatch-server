package ee.noukogu.sneakerwatch.service;

import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.repository.SneakerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilterService {

    @Resource
    private SneakerRepository repository;


    public Set<String> getBrands() {
        List<Sneaker> sneakers = new ArrayList<>();
        repository.findAll().forEach(sneakers::add);
        return sneakers.stream().map(Sneaker::getBrand).collect(Collectors.toSet());
    }

    public Set<String> getInspirations() {
        List<Sneaker> sneakers = new ArrayList<>();
        repository.findAll().forEach(sneakers::add);
        return sneakers.stream().map(Sneaker::getBrand).collect(Collectors.toSet());
    }

}
