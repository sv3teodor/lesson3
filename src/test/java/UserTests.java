import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class UserTests extends BaseTest {

    @Test
    @DisplayName("Create user test")
    public void registerUser() {
        UserProfile userProfile = new UserProfile(faker.name().firstName(), faker.internet().password());
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userProfile)
                .post(ApiRequests.USER_REGISTER)
                .then()
                .statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Login user test")
    public void loginUser() {
        UserProfile userProfile = new UserProfile(faker.name().firstName() + faker.random().nextInt(1000), faker.internet().password());
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userProfile)
                .post(ApiRequests.USER_REGISTER)
                .then()
                .statusCode(SC_CREATED);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userProfile)
                .post(ApiRequests.USER_LOGIN)
                .then()
                .statusCode(SC_OK);
    }
}


