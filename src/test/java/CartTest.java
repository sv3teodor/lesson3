import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import jdk.jfr.Description;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.*;

public class CartTest extends BaseTest {


    @Test
    @DisplayName("Get cart positive")
    public void getCartPositiveTest() throws JsonProcessingException {
        addProductToCart();
        objectMapper.readValue(RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .get(ApiRequests.CART)
                .then()
                .statusCode(SC_OK)
                .extract().asString(),Cart.class);
    }

    @Test
    @DisplayName("Get cart negative")
    public void getCartNegativeTest() {
        //Запрос корзины пользователя в которую не добавлен товар
        userToken=getToken();
        RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .get(ApiRequests.CART)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message",Matchers.equalTo("Cart not found"));
    }

    @Test
    @DisplayName("Add products to cart")
    public void addItemToCartPositiveTest() {
        addProductToCart()
                .then()
                .statusCode(SC_CREATED)
                .body("message",Matchers.equalTo("Product added to cart successfully"));
    }

    @Test
    @DisplayName("Add products to cart negative test")
    public void addItemToCartNegativeTest() {
        //Пробуем добавить продукт с несуществующим ID
        Products products = new Products(0,"fake","fake",0D,0D,0);
        addProductToCart(products,1)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message",Matchers.equalTo( "Product not found"));
    }

    @Test
    @DisplayName("Remove product positive test")
    public void removeProductFromCartPositiveTest() {
        //Тут баг. Сообщение в ответе не соответствует описанию
        Products products = getProductList()[0];
        addProductToCart(products,1);
        RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .delete(ApiRequests.CART + "/" +products.getId() )
                .then()
                .statusCode(SC_OK)
                .body("messages",Matchers.equalTo("Product removed from cart successfully"));
    }

    @Test
    @DisplayName("Remove product positive negative test")
    public void removeProductFromCartNegativeTest() {
        //Пробуем удалить продукт с несуществующим ID
        //Тут баг. Сообщение в ответе не соответствует описанию
        Products products = getProductList()[0];
        addProductToCart(products,1);
        RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .delete(ApiRequests.CART + "/" +products.getId()+1 )
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message",Matchers.equalTo( "Product not found"));
    }

}
