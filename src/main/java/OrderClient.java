import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class OrderClient extends Basic{
    private static final String PATH_ORDER = "/api/orders";
    @Step("Создание заказа")
    public ValidatableResponse postOrder(IngredientsList ingredientsList,String tokenReceived){
        return given()
                .spec(getSpec())    //.log().all()
                .header("Authorization", tokenReceived)
                .body(ingredientsList)
                .when()
                .post(PATH_ORDER)
                .then();    //.log().all()
    }
    @Step("Получить заказы конкретного пользователя")
    public ValidatableResponse getOrder(String tokenReceived){
        return given()
                .spec(getSpec())    //.log().all()
                .header("Authorization", tokenReceived)
                .when()
                .get(PATH_ORDER)
                .then();    //.log().all();
    }
}

