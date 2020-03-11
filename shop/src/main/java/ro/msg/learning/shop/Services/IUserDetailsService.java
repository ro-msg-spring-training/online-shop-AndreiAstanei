package ro.msg.learning.shop.Services;

import ro.msg.learning.shop.Entities.Customer;

import java.util.Optional;

public interface IUserDetailsService {
    Customer searchDatabaseWithCredentials(String username, String password);
}
