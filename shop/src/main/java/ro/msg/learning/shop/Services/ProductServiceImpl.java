package ro.msg.learning.shop.Services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.DTOs.ProductDTO;
import ro.msg.learning.shop.Entities.Product;
import ro.msg.learning.shop.Entities.ProductCategory;
import ro.msg.learning.shop.Entities.Supplier;
import ro.msg.learning.shop.Repositories.ProductCategoryRepository;
import ro.msg.learning.shop.Repositories.ProductRepository;
import ro.msg.learning.shop.Repositories.SupplierRepository;
import ro.msg.learning.shop.mappers.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(this::convertProductToDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Integer id) {
        ProductDTO productDTO = new ProductDTO();

        Optional<Product> receivedProductFromDatabase = productRepository.findById(id);
        if (receivedProductFromDatabase.isPresent()) {
//            productDTO = convertProductToDTO(receivedProductFromDatabase.get());
            productDTO = productMapper.mapProductToProductDTO(receivedProductFromDatabase.get());
        }

        return productDTO;
    }

    @Override
    public String deleteProductById(Integer id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return "Product deleted!";
        } else {
            return "Product not found!";
        }
    }

    @Override
    public ProductDTO updateProduct(ProductDTO newProductValues) {
        ProductDTO productDTO = new ProductDTO();

        Optional<Product> receivedProductFromDatabase = productRepository.findById(newProductValues.getId());

        // if we found the product
        if (receivedProductFromDatabase.isPresent()) {
            // Verificari pentru categorie si supplier
            Optional<ProductCategory> auxCategory = productCategoryRepository.findByNameEqualsAndDescriptionEquals(newProductValues.getProductCategoryName(), newProductValues.getProductCategoryDescription());
            Optional<Supplier> auxSupplier = supplierRepository.findByNameEquals(newProductValues.getProductSupplierName());

            // If we found the category in the databse
            if (auxCategory.isPresent()) {
                 // If the category is different from the original one, we change it
                if(!auxCategory.get().getId().equals(receivedProductFromDatabase.get().getCategory().getId())) {
                    receivedProductFromDatabase.get().setCategory(auxCategory.get());
                }

                // If we found the supplier
                if (auxSupplier.isPresent()) {
                    // If the supplier is different from the original one, we change it
                    if(!auxSupplier.get().getId().equals(receivedProductFromDatabase.get().getId())) {
                        receivedProductFromDatabase.get().setSupplier(auxSupplier.get());
                    }

                    // Actualizam datele din product entity(momentan fara categorie si supplier)
                    receivedProductFromDatabase.get().setName(newProductValues.getName());
                    receivedProductFromDatabase.get().setDescription(newProductValues.getDescription());
                    receivedProductFromDatabase.get().setPrice(newProductValues.getPrice());
                    receivedProductFromDatabase.get().setWeight(newProductValues.getWeight());
                    receivedProductFromDatabase.get().setImageUrl(newProductValues.getImageUrl());

                    // Salvam modificarile in baza de date si le pregatim pentru returnare
                    productDTO = productMapper.mapProductToProductDTO(productRepository.save(receivedProductFromDatabase.get()));
                }
            }
        }

        return productDTO;
    }

    @Override
    public Boolean createProduct(ProductDTO productData) {
        Boolean databaseResponse = true;

//        Optional<Product> databaseProduct = productRepository.find

        return databaseResponse;
    }


    private ProductDTO convertProductToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product convertDTOToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}




//        iPhone 10         Smartphones     Apple Inc
//        iPhone 10         Smartphones     Cel.ro
//        iPhone 10         Huse            Apple Inc


