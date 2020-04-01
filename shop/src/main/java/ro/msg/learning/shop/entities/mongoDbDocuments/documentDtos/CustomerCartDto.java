package ro.msg.learning.shop.entities.mongoDbDocuments.documentDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCartDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String emailAddress;
}
