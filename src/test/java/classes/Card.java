package classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Card {

    private String code;
    private String image;
    private List<String> images;
    private String value;
    private String suit;

    public Card() {
        this.code = "Riga";
        this.image = "1";
        this.value = "Riga";
        this.suit = "RI";

        String newImage = "test";
        List<String> images = new ArrayList<>();
        images.add(newImage);

        this.images = images;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")

    public void setPlaceName(String newCode) {
        this.code = newCode;
    }

    public String image() {
        return image;
    }

    public void setImage(String newImage) {
        this.image = newImage;
    }

    @JsonProperty("value")

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        this.value = newValue;
    }

    @JsonProperty("suit")

    public String getSuit() {
        return suit;
    }

    @JsonProperty("suit")

    public void setSuit(String newSuit) {
        this.suit = newSuit;
    }



}