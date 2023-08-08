package com.gorest.studentinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.PostsPojo;
import com.gorest.model.UserPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;


public class UsersSteps {

    @Step("creating user with name : {0}, email : {1}, gender : {2} and status : {3}")
    public ValidatableResponse createUsers(String Name, String email, String gender, String status){
        PostsPojo postsPojo = new PostsPojo();
        postsPojo.setName(Name);
        postsPojo.setEmail(email);
        postsPojo.setGender(gender);
        postsPojo.setStatus(status);
        return SerenityRest.given()
                .header("Content-Type","application/json")
              .header("Authorization","Bearer 729b4b499368ff27fc26cd366161b6db88400b49b82f58751588a296c6bfd498")
                //.contentType(ContentType.JSON)
                .body(postsPojo)
                .when()
                .post()
                .then();

    }
    @Step ("Getting the users information with firstname : {0}")
    public HashMap<String, Object> getUsersInfoByFirstName(String firstName) {
        String s1 = "findAll{it.firstName='";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then().statusCode(200)
                .extract()
                .path(s1 + firstName + s2);
    }


    @Step("updating user information with userID : {0}")
    public ValidatableResponse updateUsers (int userId, String Name, String email,  String status){
        UserPojo userPojo = new UserPojo();
        userPojo.setName(Name);
        userPojo.setEmail(email);
        userPojo.setStatus(status);

        return SerenityRest.given().log().all()
                .header("Authorization","Bearer 729b4b499368ff27fc26cd366161b6db88400b49b82f58751588a296c6bfd498")
                .header("Content-Type","application/json")
                .pathParam("usersID", userId)
                .when()
                .body(userPojo)
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then().statusCode(200);

    }

    @Step("Deleting the user information by userID : {0}")
    public ValidatableResponse deleteUsers(int userId) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 729b4b499368ff27fc26cd366161b6db88400b49b82f58751588a296c6bfd498")
                .headers("Content-Type", "application/json")
                .pathParam("usersID",userId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }
    @Step("verify the user by userId : {0}")
    public ValidatableResponse getUserById(int userId){
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 729b4b499368ff27fc26cd366161b6db88400b49b82f58751588a296c6bfd498")
                .headers("Content-Type", "application/json")
                .pathParam("usersID",userId)
                .when()
                .get(EndPoints.GET_USERS_BY_ID)
                .then();
    }

}


