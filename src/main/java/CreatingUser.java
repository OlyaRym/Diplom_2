import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class CreatingUser extends Basic {
    private static final String PATH = "/api/auth/register";
    private static final String PATH_EMAIL = "/api/auth/login";
    private static final String DELETE_PATCH = "/api/auth/user";
    @Step("создать уникального пользователя")
    public ValidatableResponse create(Client client) {
        return given()
                .spec(getSpec()) //.log().all()
                .body(client)
                .when()
                .post(PATH)
                .then(); //.log().all();
    }
    @Step("Авторизоваться")
    public ValidatableResponse email(Authorization authorization) {
        return given()
                .spec(getSpec()) //.log().all()
                .body(authorization)
                .when()
                .post(PATH_EMAIL)
                .then(); //.log().all();
    }
    @Step("Удаление пользователя")
    public ValidatableResponse delete(String tokenReceived) {
        return given()
                .spec(getSpec())  //.log().all()
                .header("Authorization", tokenReceived)
                .when()
                .delete(DELETE_PATCH)
                .then();  //.log().all();
    }
    @Step("Изменение данных")
    public ValidatableResponse change(Client client,String tokenReceived){
        return given()
                .spec(getSpec())  //.log().all()
                .header("Authorization", tokenReceived)
                .body(client)
                .when()
                .patch(DELETE_PATCH)
                .then(); //.log().all();
    }
}

