import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class ProductTest extends BaseTest {


    @Test
    @DisplayName("Get a list of products")
    public void getListsProducts() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .get(ApiRequests.PRODUCTS)
                .then()
                .statusCode(SC_OK);

    }

    @Test
    @DisplayName("Add a new product")
    public void addNewProduct() {
        Products products = new Products(faker.elderScrolls().city(),faker.elderScrolls().race(),
                faker.random().nextDouble(), (double) faker.random().nextLong(100));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(products)
                .post(ApiRequests.PRODUCTS)
                .then()
                .statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Get product information")
    public void getInfoProduct() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .get(ApiRequests.PRODUCTS+"/"+getProductList()[0].getId())
                .then()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Update information about product")
    public void updateInformationProduct() {
        Products products= getProductList()[0];
        products.setName(faker.animal().name());
        products.setPrice((double) faker.random().nextInt(5000));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(products)
                .put(ApiRequests.PRODUCTS+"/"+products.getId())
                .then()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Delete product")
    public void deleteProduct() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .delete(ApiRequests.PRODUCTS+"/"+getProductList()[0].getId())
                .then()
                .statusCode(SC_OK);
    }

}
