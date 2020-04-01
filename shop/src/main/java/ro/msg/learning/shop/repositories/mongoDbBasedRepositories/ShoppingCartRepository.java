package ro.msg.learning.shop.repositories.mongoDbBasedRepositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entities.mongoDbDocuments.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String>, QuerydslPredicateExecutor<ShoppingCart> {
}
