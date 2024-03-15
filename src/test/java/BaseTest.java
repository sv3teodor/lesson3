import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static org.apache.http.HttpStatus.SC_CREATED;

public abstract class BaseTest {
    private static final String BASE_URI = "http://9b142cdd34e.vps.myjino.ru:49268";
    protected static Faker faker;
    protected static ObjectMapper objectMapper;
    protected  String userToken;
    @BeforeAll
    public static void setUp() {
        faker = new Faker(Locale.US);
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);

    }

    protected String getToken() {
        UserProfile userProfile = new UserProfile(faker.name().firstName(), faker.internet().password());
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userProfile)
                .post(ApiRequests.USER_REGISTER)
                .then()
                .statusCode(SC_CREATED);

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userProfile)
                .post(ApiRequests.USER_LOGIN)
                .body().jsonPath().getString("access_token");
    }

    protected Products[] getProductList() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .get(ApiRequests.PRODUCTS).as(Products[].class);
    }

    protected Response addProductToCart() {
        return addProductToCart(getProductList()[0],1);
    }
    protected Response addProductToCart(Products products, int quantity) {
        userToken=getToken();
        return RestAssured.given()
                .auth().oauth2(userToken)
                .contentType(ContentType.JSON)
                .body(getProductList()[0])
                .body("{\"product_id\":" + products.getId() + ",\"quantity\":"+quantity+"}")
                .post(ApiRequests.CART)
                .thenReturn();
    }

}
