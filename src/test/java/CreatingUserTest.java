import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class CreatingUserTest {  //Создание пользователя
    private CreatingUser creatingUser;
    private String tokenReceived;
    @Before
    public void setUp(){
        creatingUser = new CreatingUser();
    }
    @Before
    public void tearDown() throws InterruptedException {
        Thread.sleep(600);} //вылетает ошибка 429
    @After
    //если токен не равен null, тогда удалить
    public void cleanUp(){
        if (tokenReceived != null){
            creatingUser.delete(tokenReceived);
        }
    }
    @Test
    @DisplayName("Создание клиента")
    @Description("Проверка:создать уникального пользователя")
    public void createClientTest(){
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru","123456","olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230305@yandex.ru", "123456");
        ValidatableResponse responseAuthorization = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        boolean clientCreated = responseAuthorization.extract().path("success");
        int statusCode = responseAuthorization.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Создание клиента")
    @Description("Проверка:создать пользователя, который уже зарегистрирован")
    public void  createUserWhoAlreadyRegisteredTest(){
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru","123456","olyaa");
        ValidatableResponse responseCreate = creatingUser.create(client);//дернет ручку

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230305@yandex.ru", "123456");
        ValidatableResponse responseAuthorization = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        //зарегистрироваться еще раз и дергаем ручку регистрации
        Client client1 = new Client("olya230305@yandex.ru","123456","olyaa");
        ValidatableResponse responseCreate1 = creatingUser.create(client1);

        boolean clientCreated = responseCreate1.extract().path("success");
        String clientCreated1 = responseCreate1.extract().path("message");
        int statusCode = responseCreate1.extract().statusCode();

        assertEquals(false, clientCreated);
        assertEquals("User already exists",clientCreated1);
        assertEquals(403, statusCode);
    }
    @Test
    @DisplayName("Создание клиента")
    @Description("Проверка:создать пользователя и не заполнить одно из обязательных полей")
    public void creatingCourierWithOnlyAllFieldsTest(){
        //зарегистрироваться еще раз и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru","123456","olyaa");
        client.setPassword(""); //убираем пароль
        ValidatableResponse responseCreate = creatingUser.create(client);

        boolean clientCreated = responseCreate.extract().path("success");
        String clientCreated1 = responseCreate.extract().path("message");
        int statusCode = responseCreate.extract().statusCode();

        assertEquals(false, clientCreated);
        assertEquals("Email, password and name are required fields",clientCreated1);
        assertEquals(403, statusCode);
    }
}

