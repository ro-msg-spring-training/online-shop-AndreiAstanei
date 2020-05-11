package ro.msg.learning.shop.services.shoppingCartService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.dtos.productDto.ProductDTO;
import ro.msg.learning.shop.dtos.shoppingCartDTO.DeliveryAddress;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.entities.shoppingCartEntities.ShoppingCart;
import ro.msg.learning.shop.entities.shoppingCartEntities.ShoppingCartItem;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.mappers.ShoppingCartMapper;
import ro.msg.learning.shop.repositories.jpaBasedRepositories.*;
import ro.msg.learning.shop.services.emailService.EmailServiceImpl;
import ro.msg.learning.shop.services.orderService.OrderServiceImpl;
import ro.msg.learning.shop.services.productService.ProductServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements IShoppingCartService {
    private final ProductRepository productRepository;
    private final ProductServiceImpl productService;
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final StockRepository stockRepository;
    private final ShoppingCartItemsRepository shoppingCartItemsRepository;
    private final OrderServiceImpl orderService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final EmailServiceImpl emailService;

    public static int getIndexOf(List<ShoppingCartItem> list, Integer productId) {
        int pos = 0;

        for (ShoppingCartItem myObj : list) {
            if (productId.equals(myObj.getProduct().getId()))
                return pos;
            pos++;
        }

        return -1;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productService.getProducts();
    }

    @Override
    public UserDetails getAuthenticatedUserDetails() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;

        if (principal instanceof UserDetails) {
            userDetails = ((UserDetails) principal);
        }

        return userDetails;
    }

    @Override
    public boolean addProductToCart(Integer productId) {
        UserDetails loggedInUserDetails = getAuthenticatedUserDetails();

        Optional<Customer> customer = customerRepository.findByUsernameEquals(loggedInUserDetails.getUsername());

        if (customer.isPresent()) {
            ShoppingCart customerShoppingCart = customer.get().getShoppingCart();

            if (customerShoppingCart != null) {
                // if the user already has a cart, then just add/update the product

                // check the existence of the product
                if (productAlreadyExistsInShoppingCart(customerShoppingCart, productId)) {
                    ShoppingCartItem cartItem = customerShoppingCart.getShoppingCartProducts().get(getIndexOf(customerShoppingCart.getShoppingCartProducts(), productId));

                    if (productExistsInDemandedQuantity(productId, cartItem.getQuantity() + 1)) {
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        shoppingCartRepository.save(customerShoppingCart);
                        shoppingCartItemsRepository.save(cartItem);

                        return true;
                    }
                } else {
                    if (productExistsInDemandedQuantity(productId, 1)) {
                        ShoppingCartItem newCartItem = ShoppingCartItem.builder()
                                .product(productRepository.findById(productId).get())
                                .quantity(1)
                                .shoppingCart(customerShoppingCart)
                                .build();

                        customerShoppingCart.getShoppingCartProducts().add(newCartItem);
                        shoppingCartRepository.save(customerShoppingCart);
                        shoppingCartItemsRepository.save(newCartItem);

                        return true;
                    }
                }
            } else {
                // Generate the cart and attempt to add the product again
                generateCustomerShoppingCart(customer.get());
                this.addProductToCart(productId);
            }
        }

        return false;
    }

    @Override
    public ShoppingCart getCustomerShoppingCart() {
        UserDetails loggedInUserDetails = getAuthenticatedUserDetails();

        if (loggedInUserDetails == null)
            return null;

        Optional<Customer> customer = customerRepository.findByUsernameEquals(loggedInUserDetails.getUsername());

        return customer.map(Customer::getShoppingCart).orElse(null);

    }

    @Override
    public void decreaseProductQuantity(Integer productId) {
        UserDetails loggedInUserDetails = getAuthenticatedUserDetails();

        Optional<Customer> customer = customerRepository.findByUsernameEquals(loggedInUserDetails.getUsername());

        if (customer.isPresent()) {
            ShoppingCart cart = customer.get().getShoppingCart();

            if (cart != null) {
                List<ShoppingCartItem> cartItems = cart.getShoppingCartProducts();

                if (cartItems.size() > 0) {
                    int productIndex = getIndexOf(cartItems, productId);
                    ShoppingCartItem cartItem = cartItems.get(productIndex);

                    if (cartItem.getQuantity() > 1) {
                        cartItem.setQuantity(cartItem.getQuantity() - 1);

                        shoppingCartItemsRepository.save(cartItem);
                    } else if (cartItem.getQuantity() <= 1) {
                        shoppingCartItemsRepository.delete(cartItem);
                    }
                }
            }
        }
    }

    @Override
    public void increaseProductQuantity(Integer productId) {
        UserDetails loggedInUserDetails = getAuthenticatedUserDetails();

        Optional<Customer> customer = customerRepository.findByUsernameEquals(loggedInUserDetails.getUsername());

        if (customer.isPresent()) {
            ShoppingCart cart = customer.get().getShoppingCart();

            if (cart != null) {
                List<ShoppingCartItem> cartItems = cart.getShoppingCartProducts();

                if (cartItems.size() > 0) {
                    int productIndex = getIndexOf(cartItems, productId);
                    ShoppingCartItem cartItem = cartItems.get(productIndex);

                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    shoppingCartItemsRepository.save(cartItem);
                }
            }
        }
    }

    @Override
    public void deleteProductFromCart(Integer productId) {
        UserDetails loggedInUserDetails = getAuthenticatedUserDetails();

        Optional<Customer> customer = customerRepository.findByUsernameEquals(loggedInUserDetails.getUsername());

        if (customer.isPresent()) {
            ShoppingCart cart = customer.get().getShoppingCart();

            if (cart != null) {
                List<ShoppingCartItem> cartItems = cart.getShoppingCartProducts();

                if (cartItems.size() > 0) {
                    int productIndex = getIndexOf(cartItems, productId);

                    if (productIndex != -1) {
                        ShoppingCartItem cartItem = cartItems.get(productIndex);
                        shoppingCartItemsRepository.delete(cartItem);
                    }
                }
            }
        }
    }

    public boolean placeOrder(DeliveryAddress deliveryAddress) {
        boolean operationStatus = false;
        OrderDTOInput orderDTOInput = null;
        Customer customer = getLoggedInUser();

        if (customer != null) {

            ShoppingCart cart = customer.getShoppingCart();

            if (cart != null) {
                List<ShoppingCartItem> cartItems = cart.getShoppingCartProducts();

                if (cartItems.size() > 0) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    orderDTOInput = OrderDTOInput.builder()
                            .customerId(customer.getId())
                            .addressCountry(deliveryAddress.getCountry())
                            .addressCounty(deliveryAddress.getCounty())
                            .addressCity(deliveryAddress.getCity())
                            .addressStreetAddress(deliveryAddress.getStreetAddress())
                            .order_timestamp(-120)
                            .createdAt(LocalDateTime.now().format(formatter))
                            .productsList(generateSimpleProductQuantityListFromShoppingCartItem(cartItems))
                            .build();

                    try {
                        OrderDTOOutput orderDTOOutput = orderService.generateOrder(orderDTOInput);

                        if (orderDTOOutput != null) {
                            operationStatus = true;
                            shoppingCartItemsRepository.deleteAll(cartItems);

                            // Email Service
                            emailService.sendHtmlEmail(customer, orderDTOOutput);
//                            emailService.sendPlainTextEmail(customer, orderDTOOutput);
                        }
                    } catch (OrderPlacingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return operationStatus;
    }

    private List<SimpleProductQuantity> generateSimpleProductQuantityListFromShoppingCartItem(List<ShoppingCartItem> cartItems) {
        return cartItems.stream().map(shoppingCartMapper::mapShoppingCartItemToSimpleProductQuantity).collect(Collectors.toList());
    }

    private Customer getLoggedInUser() {
        UserDetails loggedInUserDetails = getAuthenticatedUserDetails();

        Optional<Customer> customer = customerRepository.findByUsernameEquals(loggedInUserDetails.getUsername());

        return customer.orElse(null);
    }

    private boolean productExistsInDemandedQuantity(Integer productId, int quantity) {
        return quantity <= stockRepository.getTotalQuantityForProduct(productId);
    }

    private boolean productAlreadyExistsInShoppingCart(ShoppingCart customerShoppingCart, Integer productId) {
        for (ShoppingCartItem item : customerShoppingCart.getShoppingCartProducts()) {
            if (item.getProduct().getId().equals(productId))
                return true;
        }

        return false;
    }

    private void generateCustomerShoppingCart(Customer customer) {
        ShoppingCart cart = ShoppingCart.builder()
                .customer(customer)
                .shoppingCartProducts(Collections.emptyList())
                .build();

        customer.setShoppingCart(cart);

        customerRepository.save(customer);
        shoppingCartRepository.save(cart);
    }
}
