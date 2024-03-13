import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class CartTest extends BaseTest{



    @Test
    @DisplayName("Get cart")
    public void getCart() {
        String userToken= getToken();
        //Add product to cart
        RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .body("{\"product_id\":"+getProductList()[0].getId()+",\"quantity\":2}")
                .post(ApiRequests.CART)
                .then()
                .statusCode(SC_CREATED);

        RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .get(ApiRequests.CART)
                .then()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Add products to cart")
    public void addItemToCart() {
        RestAssured.given()
                .auth().oauth2(getToken())
                .contentType(ContentType.JSON)
                .body("{\"product_id\":"+getProductList()[0].getId()+",\"quantity\":2}")
                .post(ApiRequests.CART)
                .then()
                .statusCode(SC_CREATED);

    }


    @Test
    @DisplayName("Remove product")
    public void removeProductFromCart() {
        String userToken= getToken();
        String productId = Integer.toString(getProductList()[0].getId());
        //Add product to cart
        RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .body("{\"product_id\":"+productId+",\"quantity\":2}")
                .post(ApiRequests.CART)
                .then()
                .statusCode(SC_CREATED);

        RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .get(ApiRequests.CART)
                .then()
                .statusCode(SC_OK);

        RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .delete(ApiRequests.CART+"/"+productId)
                .then()
                .statusCode(SC_OK);

    }

}
