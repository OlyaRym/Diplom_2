import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class ChangingUserDataTest {  //Изменение данных пользователя
    private CreatingUser creatingUser;
    private String tokenReceived;
    @Before
    public void setUp() {
        creatingUser = new CreatingUser();
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
    @DisplayName("Изменение данных пользователя")
    @Description("Проверка:изменить имя с авторизацией")
    public void changeNameTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru", "123456", "olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230305@yandex.ru", "123456");
        ValidatableResponse responseAuthorization = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        //изменили имя, дернули ручку изменения данных
        client.setName("olya");//изменили имя
        ValidatableResponse responseChange= creatingUser.change(client,tokenReceived);

        boolean clientCreated = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Проверка:изменить пароль с авторизацией")
    public void changePasswordTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru", "123456", "olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230305@yandex.ru", "123456");
        ValidatableResponse responseAuthorization = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        //изменили пароль, дернули ручку изменения данных
        client.setPassword("87654896");//смена паролья
        ValidatableResponse responseChange= creatingUser.change(client,tokenReceived);

        boolean clientCreated = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Проверка:изменить почту с авторизацией")
    public void changeEmailTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru", "123456", "olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //авторизация зарег.клиента, дергаем ручку регистрации и получаем токен
        Authorization authorization = new Authorization("olya230305@yandex.ru", "123456");
        ValidatableResponse responseAuthorization = creatingUser.email(authorization);
        tokenReceived = responseAuthorization.extract().path("accessToken");

        //изменили почту, дернули ручку изменения данных
        client.setEmail("891@yandex.ru");
        ValidatableResponse responseChange= creatingUser.change(client,tokenReceived);

        boolean clientCreated = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();
        assertEquals(200, statusCode);
        assertEquals(true, clientCreated);
    }
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Проверка:изменить имя без авторизации")
    public void changeNameWithoutAuthorizationTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru", "123456", "olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //изменили имя без авторизации, дернули ручку изменения данных
        client.setName("Sergey");
        ValidatableResponse responseChange= creatingUser.change(client,"");

        boolean clientCreated = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();
        assertEquals(401, statusCode);
        assertEquals(false, clientCreated);
    }
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Проверка:изменить почту без авторизацией")
    public void changeEmailWithoutAuthorizationTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru", "123456", "olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //изменили почту без авторизации, дернули ручку изменения данных
        client.setEmail("91150@yandex.ru");
        ValidatableResponse responseChange= creatingUser.change(client,"");

        boolean clientCreated = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();
        assertEquals(401, statusCode);
        assertEquals(false, clientCreated);
    }
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Проверка:изменить пароль без авторизации")
    public void changePasswordWithoutAuthorizationTest() {
        //зарегистрироваться и дергаем ручку регистрации
        Client client = new Client("olya230305@yandex.ru", "123456", "olyaaa");
        ValidatableResponse responseCreate = creatingUser.create(client);

        //изменили пароль без авторизации, дернули ручку изменения данных
        client.setPassword("25963212");
        ValidatableResponse responseChange= creatingUser.change(client,"");

        boolean clientCreated = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();
        assertEquals(401, statusCode);
        assertEquals(false, clientCreated);
    }
}
