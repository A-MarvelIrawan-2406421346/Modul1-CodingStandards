package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb5589f-1c39-46e0-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb5589f-1c39-46e0-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateGeneratesIdWhenIdNull() {
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("Produk Tanpa ID");
        product.setProductQuantity(1);

        Product created = productRepository.create(product);

        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isEmpty());
    }


    @Test
    void testCreateGeneratesIdWhenIdEmpty() {
        Product product = new Product();
        product.setProductId("");
        product.setProductName("Produk ID String Kosong");
        product.setProductQuantity(2);

        Product created = productRepository.create(product);

        assertNotNull(created.getProductId());
        assertNotEquals("", created.getProductId());
    }

    @Test
    void testFindByIdFoundAndNotFound() {
        Product product = new Product();
        product.setProductId("id-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product found = productRepository.findById("id-1");
        assertNotNull(found);
        assertEquals("id-1", found.getProductId());

        Product notFound = productRepository.findById("ga-ada");
        assertNull(notFound);
    }

    @Test
    void testDeleteById() {
        // Setup data
        Product product = new Product();
        product.setProductId("id-to-delete");
        product.setProductName("Barang Dihapus");
        product.setProductQuantity(10);
        productRepository.create(product);

        assertNotNull(productRepository.findById("id-to-delete"));

        productRepository.deleteById("id-to-delete");

        assertNull(productRepository.findById("id-to-delete"));

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteByNonExistentId() {
        // Setup data
        Product product = new Product();
        product.setProductId("id-aman");
        product.setProductName("Barang Aman");
        product.setProductQuantity(10);
        productRepository.create(product);

        assertDoesNotThrow(() -> productRepository.deleteById("id-ngasal"));

        assertNotNull(productRepository.findById("id-aman"));
    }
}