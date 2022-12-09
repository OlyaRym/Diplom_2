import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;
public class ReceivingOrdersFromSpecificUserTest {
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
        Thread.sleep(600);} //вылетает ошибка 429
    @After
    public void cleanUp() {
        if (tokenReceived != null){
            creatingUser.delete(tokenReceived);
        }
    }
    @Test
    @DisplayName("Получение заказов конкретного пользователя")
    @Description("Проверка:получить заказ авторизованного пользователя")
    public void receivingOrdersAuthorizedUserTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230308@yandex.ru", "123456", "olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230308@yandex.ru", "123456");
        ValidatableResponse responseAuthorization = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        // создание заказа, дергаем ручку создания заказа
        IngredientsList ingredientsList = new IngredientsList(List.of("61c0c5a71d1f82001bdaaa6d"));
        ValidatableResponse responseOrder = orderClient.postOrder(ingredientsList,tokenReceived);

        //дергаем ручку получения заказа пользователя
        ValidatableResponse responseGet = orderClient.getOrder(tokenReceived);

        boolean clientCreated = responseGet.extract().path("success");
        int statusCode = responseGet.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Получение заказов конкретного пользователя")
    @Description("Проверка:получить заказ неавторизованного пользователя")
    public void receivingOrdersUnauthorizedUserTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230308@yandex.ru", "123456", "olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        // создание заказа, дергаем ручку создания заказа
        IngredientsList ingredientsList = new IngredientsList(List.of("61c0c5a71d1f82001bdaaa6d"));
        ValidatableResponse responseOrder = orderClient.postOrder(ingredientsList,"");

        //дергаем ручку получения заказа пользователя
        ValidatableResponse responseGet = orderClient.getOrder("");

        boolean clientCreated = responseGet.extract().path("success");
        int statusCode = responseGet.extract().statusCode();
        assertEquals(401, statusCode);
        assertEquals(false, clientCreated);
    }
}

