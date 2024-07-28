import classes.Draw;
import classes.User;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;

import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)

public class UserTests {

    @Test
    public void getUsers(){

        given().
                when().
                get("http://localhost:5000/users").
                then().
                log().body();

    }

    @Test
    public void checkUserName() {


        List<User> users =

                given().
                when().
                    get("http://localhost:5000/users").
                as(new TypeRef<List<User>>() {});

        assertEquals(users.get(0).getFirstName(), "John");

    }

    @Test
    public void postNewUser(){
        User user = new User();
        user.setFirstName("Ewan");
        user.setLastName("Laing");
        user.setEmail("testmail.com");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("http://localhost:5000/users")
                .then()
                .statusCode(201)
                .log().body();

        given().
                when().
                get("http://localhost:5000/users").
                then().
                log().body();

        List<User> users =

                given().
                        when().
                        get("http://localhost:5000/users").
                        as(new TypeRef<List<User>>() {});

        Integer index = users.size() - 1;

        assertEquals("Expected first name to be Ewan, but instead was " + users.get(index).getFirstName(),"Ewan", users.get(index).getFirstName());

        String id = users.get(index).getId();


        given().
                pathParam("id", id)
                .when()
                .delete("http://localhost:5000/users/{id}")
                .then()
                .log().body();

        users =

                given().
                        when().
                        get("http://localhost:5000/users").
                        as(new TypeRef<List<User>>() {});

        for(int counter = 0; counter < users.size(); counter ++){
            assertNotEquals("Ewan", users.get(counter).getFirstName());
        }


    }

    @Test
    public void checkUserNameThroughIDSearch() {


        List<User> users =

                given().
                        when().
                        get("http://localhost:5000/users").
                        as(new TypeRef<List<User>>() {});

        String id = users.get(1).getId();

        User user =

                given().
                    pathParam("id", id).
                when().
                    get("http://localhost:5000/users/{id}").
                    as(new TypeRef<User>() {});

        assertEquals(user.getFirstName(), "Alice");




    }

    @Test
    public void deleteNewUser() {

        User user = new User();
        user.setFirstName("Doomed");
        user.setLastName("User");
        user.setEmail("userToDelete.com");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("http://localhost:5000/users")
                .then()
                .statusCode(201)
                .log().body();

        List<User> users =

                given().
                        when().
                        get("http://localhost:5000/users").
                        as(new TypeRef<List<User>>() {});

        Integer index = users.size() - 1;

        assertEquals("Expected first name to be Doomed, but instead was " + users.get(index).getFirstName(),"Doomed", users.get(index).getFirstName());

        String id = users.get(index).getId();


             given().
                pathParam("id", id)
            .when()
            .delete("http://localhost:5000/users/{id}")
            .then()
            .log().body();

        users =

                given().
                        when().
                        get("http://localhost:5000/users").
                        as(new TypeRef<List<User>>() {});

        for(int counter = 0; counter < users.size(); counter ++){
            assertNotEquals("Doomed", users.get(counter).getFirstName());
        }

    }

    @Test
    public void updateUser() {

        User user = new User();
        user.setFirstName("User");
        user.setLastName("Version1");
        user.setEmail("userToPatch.com");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("http://localhost:5000/users")
                .then()
                .statusCode(201)
                .log().body();

        List<User> users =

                given().
                        when().
                        get("http://localhost:5000/users").
                        as(new TypeRef<List<User>>() {});

        Integer index = users.size() - 1;

        assertEquals("Expected last name to be Version1, but instead was " + users.get(index).getLastName(),"Version1", users.get(index).getLastName());

        String id = users.get(index).getId();

        Map<String, String> updateData = new HashMap<>();
        updateData.put("last_name", "Version2");

        given().
                pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(updateData)
                .when()
                .patch("http://localhost:5000/users/{id}")
                .then()
                .log().body();

        users =

                given().
                        when().
                        get("http://localhost:5000/users").
                        as(new TypeRef<List<User>>() {});

        for(int counter = 0; counter < users.size(); counter ++){
            assertNotEquals("Version1", users.get(counter).getLastName());
        }
        User userTest =

                given().
                        pathParam("id", id).
                        when().
                        get("http://localhost:5000/users/{id}").
                        as(new TypeRef<User>() {});

        assertEquals(userTest.getLastName(), "Version2");




        given().
                pathParam("id", id)
                .when()
                .delete("http://localhost:5000/users/{id}")
                .then()
                .log().body();

        users =

                given().
                        when().
                        get("http://localhost:5000/users").
                        as(new TypeRef<List<User>>() {});

        for(int counter = 0; counter < users.size(); counter ++){
            assertNotEquals("Version2", users.get(counter).getLastName());
        }

    }





}
