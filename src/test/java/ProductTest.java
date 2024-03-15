import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.*;

public class ProductTest extends BaseTest {

    @Test
    @DisplayName("Get a list of products")
    public void getListsProducts() throws JsonProcessingException {
        objectMapper.readValue(RestAssured.given()
                .contentType(ContentType.JSON)
                .get(ApiRequests.PRODUCTS)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .asString(), Products[].class);
    }

    @Test
    @DisplayName("Add a new product")
    public void addNewProductPositiveTest() {
        Products products = new Products(0,faker.elderScrolls().city(),faker.elderScrolls().city(),
                faker.random().nextDouble(),faker.random().nextDouble(),faker.random().nextInt(10));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(products)
                .post(ApiRequests.PRODUCTS)
                .then()
                .statusCode(SC_CREATED)
                .body("messages", Matchers.equalTo("Product added successfully"));
    }

    @Test
    @DisplayName("Get product information positive test")
    public void getInfoProductPositiveTest() throws JsonProcessingException {
        //Тут баг. Должен вернутся один продукт, а по факту возвращается массив из одного элемента
        objectMapper.readValue(RestAssured.given()
                .contentType(ContentType.JSON)
                .get(ApiRequests.PRODUCTS + "/" + getProductList()[0].getId())
                .then()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .body()
                .asString(),Products.class);
    }


    @Test
    @DisplayName("Update information about product")
    public void updateInformationProductPositiveTest() {
        Products products = getProductList()[0];
        products.setName(faker.animal().name());
        products.setPrice((double) faker.random().nextInt(5000));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(products)
                .put(ApiRequests.PRODUCTS + "/" + products.getId())
                .then()
                .statusCode(SC_OK)
                .body("messages", Matchers.equalTo("Product updated successfully"));
    }

    @Test
    @DisplayName("Update information about product. Negative test")
    public void updateInformationProductNegativeTest() {
        Products products = getProductList()[0];
        products.setName(faker.animal().name());
        products.setPrice((double) faker.random().nextInt(5000));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(products)
                .put(ApiRequests.PRODUCTS + "/" + products.getId())
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("messages", Matchers.equalTo("Product not found"));
    }

    @Test
    @DisplayName("Delete product. Positive test")
    public void deleteProductPositiveTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .delete(ApiRequests.PRODUCTS + "/" + getProductList()[0].getId())
                .then()
                .statusCode(SC_OK)
                .body("messages", Matchers.equalTo("Product deleted successfully"));
    }
    @Test
    @DisplayName("Delete product. Negative test")
    public void deleteProductNegativeTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .delete(ApiRequests.PRODUCTS + "/" + getProductList()[0].getId())
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("messages", Matchers.equalTo("Product not found"));
    }

}
