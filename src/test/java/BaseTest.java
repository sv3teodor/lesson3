import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;

public abstract class BaseTest {
    protected static Faker faker;
    private static final String BASE_URI="http://9b142cdd34e.vps.myjino.ru:49268";

    @BeforeAll
    public static  void setUp() {
        faker = new Faker(Locale.US);
        RestAssured.baseURI=BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected String getToken() {
        UserProfile userProfile = new UserProfile(faker.name().firstName(),faker.internet().password());
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
}
