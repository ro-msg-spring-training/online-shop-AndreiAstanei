package ro.msg.learning.shop.entities.mongoDbDocuments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.entities.mongoDbDocuments.documentDtos.CustomerCartDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ShoppingCarts")
public class ShoppingCart {
    @Id
    private String id;
    private CustomerCartDto customer;
    private List<SimpleProductQuantity> productsList;
}
