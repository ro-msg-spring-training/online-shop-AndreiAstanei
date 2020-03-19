package ro.msg.learning.shop.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.Entities.Customer;
import ro.msg.learning.shop.Repositories.CustomerRepository;
import ro.msg.learning.shop.mappers.CustomerSecurityDetails;

import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> requestedUser;

        requestedUser = customerRepository.findByUsernameEquals(username);

        if (requestedUser.isPresent()) {
            return new CustomerSecurityDetails(requestedUser.get());
        } else {
            throw new UsernameNotFoundException("Wrong credentials");
        }
    }
}
