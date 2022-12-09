import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class UserLoginTest { //Логин пользователя
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
    public void cleanUp(){
        if (tokenReceived != null){
            creatingUser.delete(tokenReceived);
        }
    }
    @Test
    @DisplayName("Логин пользователя")
    @Description("Проверка:логин под существующим пользователем")
    public void authorizationExistingUserTest(){
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230@yandex.ru","123456","olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230@yandex.ru","123456");
        ValidatableResponse responseAuthorization  = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        boolean clientCreated = responseAuthorization.extract().path("success");
        int statusCode = responseAuthorization.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Логин пользователя")
    @Description("Проверка:логин с неверным логином")
    public void withInvalidUsernameTest(){
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230@yandex.ru","123456","olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya0@yandex.ru","123456");
        ValidatableResponse responseAuthorization  = creatingUser.email(authorization);

        boolean clientCreated = responseAuthorization.extract().path("success");
        int statusCode = responseAuthorization.extract().statusCode();
        assertEquals(401, statusCode);
        assertEquals(false, clientCreated);
    }
    @Test
    @DisplayName("Логин пользователя")
    @Description("Проверка:логин с неверным паролем")
    public void withInvalidPasswordTest(){
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230@yandex.ru","123456","olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230@yandex.ru","1234");
        ValidatableResponse responseAuthorization  = creatingUser.email(authorization);

        boolean clientCreated = responseAuthorization.extract().path("success");
        int statusCode = responseAuthorization.extract().statusCode();
        assertEquals(401, statusCode);
        assertEquals(false, clientCreated);
    }
}
