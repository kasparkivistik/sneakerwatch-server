package ee.noukogu.sneakerwatch.service;

import ee.noukogu.sneakerwatch.model.Budget;
import ee.noukogu.sneakerwatch.model.Sneaker;
import ee.noukogu.sneakerwatch.repository.SneakerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SneakerService {

    @Resource
    SneakerRepository repository;

    public Sneaker add(Sneaker sneaker) {
        return repository.save(sneaker);
    }

    public List<Sneaker> getAll(Sneaker sneaker, Budget budget) {
        return null;
    }
}
