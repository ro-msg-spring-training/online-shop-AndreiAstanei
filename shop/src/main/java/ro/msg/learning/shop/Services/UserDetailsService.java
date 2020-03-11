package ro.msg.learning.shop.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.Entities.Customer;
import ro.msg.learning.shop.Repositories.CustomerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements IUserDetailsService {
    private final CustomerRepository customerRepository;


    @Override
    public Customer searchDatabaseWithCredentials(String username, String password) {
        Optional<Customer> requestedUser;

        requestedUser = customerRepository.findByUsernameEqualsAndPasswordEquals(username, password);

        return requestedUser.orElse(null);
    }
}
