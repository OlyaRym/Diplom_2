import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
public class CreatingOrderTest {
    private CreatingUser creatingUser;
    private OrderClient orderClient;
    private String tokenReceived;
    @Before
    public void setUp() {
        creatingUser = new CreatingUser();
        orderClient = new OrderClient();
    }
    @Before
    public void tearDown() throws InterruptedException {
        Thread.sleep(900);} //вылетает ошибка 429
    @After
    public void cleanUp() {
        if (tokenReceived != null){
            creatingUser.delete(tokenReceived);
        }
    }
    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка: создание заказа с авторизацией")
    public void CreatingOrderWithAuthorizationTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230309@yandex.ru","123456","olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230309@yandex.ru","123456");
        ValidatableResponse responseAuthorization  = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        // создание заказа, дергаем ручку создания заказа
        IngredientsList ingredientsList = new IngredientsList(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        ValidatableResponse responseOrder = orderClient.postOrder(ingredientsList,tokenReceived);

        boolean clientCreated = responseOrder.extract().path("success");
        int statusCode = responseOrder.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка: создание заказа без авторизации")
    public void CreatingOrderWithoutAuthorizationTest() {
        // создание заказа, дергаем ручку создания заказа
        IngredientsList ingredientsList = new IngredientsList(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        ValidatableResponse responseOrder = orderClient.postOrder(ingredientsList,"");

        boolean clientCreated = responseOrder.extract().path("success");
        int statusCode = responseOrder.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка: создание заказа с ингредиентами")
    public void CreatingOrderWithIngredientsTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230309@yandex.ru","123456","olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230309@yandex.ru","123456");
        ValidatableResponse responseAuthorization  = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        // создание заказа, дергаем ручку создания заказа
        IngredientsList ingredientsList = new IngredientsList(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        ValidatableResponse responseOrder = orderClient.postOrder(ingredientsList,tokenReceived);

        boolean clientCreated = responseOrder.extract().path("success");
        int statusCode = responseOrder.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка: создание заказа без ингредиентов")
    public void CreatingOrderWithoutIngredientsTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230309@yandex.ru","123456","olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230309@yandex.ru","123456");
        ValidatableResponse responseAuthorization  = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        // создание заказа, дергаем ручку создания заказа
        IngredientsList ingredientsList = new IngredientsList(List.of());
        ValidatableResponse responseOrder = orderClient.postOrder(ingredientsList,tokenReceived);

        boolean clientCreated = responseOrder.extract().path("success");
        int statusCode = responseOrder.extract().statusCode();
        assertEquals(400, statusCode);
        assertEquals(false, clientCreated);
    }
    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка: создание заказа с неверным хешем ингредиентов")
    public void CreatingOrderWithIncorrectHashIngredientsTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230309@yandex.ru","123456","olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230309@yandex.ru","123456");
        ValidatableResponse responseAuthorization  = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        // создание заказа, дергаем ручку создания заказа
        IngredientsList ingredientsList = new IngredientsList(List.of("61c0c5a71d1f82001bda"));
        ValidatableResponse responseOrder = orderClient.postOrder(ingredientsList,tokenReceived);

        int statusCode = responseOrder.extract().statusCode();
        assertEquals(500, statusCode);
    }
}
