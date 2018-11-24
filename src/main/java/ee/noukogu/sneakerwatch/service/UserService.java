package ee.noukogu.sneakerwatch.service;

import ee.noukogu.sneakerwatch.model.User;
import ee.noukogu.sneakerwatch.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User getById(long id) {
        return userRepository.getById(id);
    }

    public User add(User user) {
        return userRepository.save(user);
    }
}
