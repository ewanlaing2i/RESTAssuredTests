package classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"first_name", "last_name", "email"})


public class User {


    private String first_name;
    private String last_name;
    private String email;
    private String id;

    public User() {
        this.first_name = "Joe";
        this.last_name = "Example";
        this.email = "something@anything.com";
        this.id = "id";
    }



    @JsonProperty("first_name")

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String name) {
        this.first_name = name;
    }

    @JsonProperty("last_name")

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String name) {
        this.last_name = name;
    }

    @JsonProperty("email")

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId(){return id;}



}