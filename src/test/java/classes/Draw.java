package classes;

import classes.Card;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"success", "deck_id", "cards", "remaining"})

public class Draw {

    private Boolean success;
    private String deck_id;
    private List<Card> cards;
    private Integer remaining;

    Draw() {
        this.success = true;
        this.deck_id = "";

        Card card = new Card();
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        this.cards = cards;

        this.remaining = 0;
    }


    public String getDeck_id() {
        return deck_id;
    }

    public Boolean getSuccess() {
        return success;
    }


    @JsonProperty("remaining")

    public Integer getRemaining() {
        return remaining;
    }



    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards)
    {
        this.cards = cards;
    }

}