package ro.msg.learning.shop.services.emailService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ro.msg.learning.shop.dtos.emailDTO.EmailOrderDTO;
import ro.msg.learning.shop.dtos.emailDTO.EmailProductListItem;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.dtos.shoppingCartDTO.DeliveryAddress;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.repositories.jdbcBasedRepositories.JDBCBasedProductRepository;
import ro.msg.learning.shop.repositories.jpaBasedRepositories.CustomerRepository;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender emailSender;
    private final JDBCBasedProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Value(value = "${email_subject_order_summary}")
    private String email_subject;

    private Context context;

    public void sendHtmlEmail(Customer customer, OrderDTOOutput order) {
        try {
            EmailOrderDTO orderDetails = generateEmailData(order);

            if (orderDetails != null) {
                context = generateContext(orderDetails);

                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setTo(customer.getEmailAddress());
                helper.setSubject(email_subject);

                String htmlTemplate = templateEngine.process("order-email-html.html", context);
                helper.setText(htmlTemplate, true);

                emailSender.send(message);
            }

        } catch (javax.mail.MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public void sendPlainTextEmail(Customer customer, OrderDTOOutput order) {
        try {
            EmailOrderDTO orderDetails = generateEmailData(order);

            if (orderDetails != null) {
                context = generateContext(orderDetails);

                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

                helper.setTo(customer.getEmailAddress());
                helper.setSubject(email_subject);

                String plainTextTemplate = templateEngine.process("order-email-plain-text.html", context);
                helper.setText(plainTextTemplate, false);

                emailSender.send(message);
            }

        } catch (javax.mail.MessagingException ex) {
            ex.printStackTrace();
        }
    }

    private EmailOrderDTO generateEmailData(OrderDTOOutput initialOrderData) {
        Optional<Customer> customer = customerRepository.findById(initialOrderData.getCustomerId());
        List<EmailProductListItem> productListItemList = generareEmailProductList(initialOrderData.getProductsList());
        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .country(initialOrderData.getCountry())
                .county(initialOrderData.getCounty())
                .city(initialOrderData.getCity())
                .streetAddress(initialOrderData.getStreetAddress())
                .build();

        if (customer.isPresent() && productListItemList != null) {
            return EmailOrderDTO.builder()
                    .orderId(initialOrderData.getId())
                    .customerName(customer.get().getFirstName() + " " + customer.get().getLastName())
                    .productListItemList(productListItemList)
                    .deliveryAddress(deliveryAddress)
                    .orderCreationTime(initialOrderData.getCreatedAt())
                    .build();
        } else {
            return null;
        }
    }

    private List<EmailProductListItem> generareEmailProductList(List<SimpleProductQuantity> productsList) {
        boolean allProductsArePresent = true;
        List<Product> products = new ArrayList<>();
        List<Optional<Product>> optionalProducts = productsList.stream().map(product -> productRepository.findById(product.getProductId())).collect(Collectors.toList());

        for (Optional<Product> product : optionalProducts) {
            if (!product.isPresent()) {
                allProductsArePresent = false;
                break;
            } else {
                products.add(product.get());
            }
        }

        if (allProductsArePresent) {
            return products.stream()
                    .map(
                            product -> EmailProductListItem.builder()
                                    .productName(product.getName())
                                    .productCategory(product.getCategory().getName())
                                    .productQuantity(findQuantityByProductId(productsList, product.getId()))
                                    .productPriceTotal(product.getPrice() * findQuantityByProductId(productsList, product.getId()))
                                    .build()
                    ).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private Integer findQuantityByProductId(List<SimpleProductQuantity> productsList, Integer id) {
        for (SimpleProductQuantity product : productsList) {
            if (product.getProductId().equals(id)) {
                return product.getProductQuantity();
            }
        }

        return 0;
    }

    private Context generateContext(EmailOrderDTO orderDetails) {
        Context context = new Context();
        context.setVariable("order", orderDetails);

        return context;
    }
}
