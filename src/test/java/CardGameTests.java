import classes.Card;
import classes.Draw;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static java.lang.Integer.parseInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(DataProviderRunner.class)



public class CardGameTests {

    @Test
    public void initialCardShuffle(){
        given().
        when().
            get("https://www.deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1").
        then().
            log().body().
        and().
            assertThat().body("success", equalTo(true));
    }

    @Test
    public void drawACardFromNewDeck(){

        given().
        when().
            get("https://www.deckofcardsapi.com/api/deck/new/draw/?count=18").
        then().
            log().body().
        and().
            assertThat().body("success", equalTo(true)).
        and().
            assertThat().body("remaining", equalTo(52 - 18));
    }

    @Test
    public void topCardWins(){

        Draw draw =

        given().
        when().
            get("https://www.deckofcardsapi.com/api/deck/new/draw/?count=2").
        as(Draw.class);

        String id = draw.getDeck_id();
        Card card1 = draw.getCards().get(0);
        Card card2 = draw.getCards().get(1);

        HashMap<String, String> royalty = new HashMap<>();
        royalty.put("JACK", "11");
        royalty.put("QUEEN", "12");
        royalty.put("KING", "13");
        royalty.put("ACE", "14");

        List<String> royalValues = new ArrayList<String>(royalty.keySet());

        String value1String = card1.getValue();
        String value2String = card2.getValue();

        String suite1 = card1.getSuit();
        String suite2 = card2.getSuit();

        String player1card = value1String + " of " + suite1;
        String player2card = value2String + " of " + suite2;

        if(royalValues.contains(value1String)){
            value1String = royalty.get(value1String);
        }
        if(royalValues.contains(value2String)){
            value2String = royalty.get(value2String);
        }

        Integer value1 = parseInt(value1String);
        Integer value2 = parseInt(value2String);

        HashMap<String, Integer> tieBreaker = new HashMap<>();
        tieBreaker.put("SPADES", 3);
        tieBreaker.put("DIAMONDS", 2);
        tieBreaker.put("HEARTS", 1);
        tieBreaker.put("CLUBS", 0);

        if(value1.equals(value2)){
            value1 += tieBreaker.get(suite1);
            value2 += tieBreaker.get(suite2);
        }

        if(value1 > value2){
            System.out.println("PLAYER 1 WINS - " + player1card + " beats " + player2card);
        } else if (value2 > value1) {
            System.out.println("PLAYER 2 WINS - " + player2card + " beats " + player1card);
        }else{
            System.out.println("It's a TIE, SOMEHOW?!");
        }


        given().
                when().
                get("https://www.deckofcardsapi.com/api/deck/"+ id +"/shuffle/?remaining=true").
                then().
                log().body().
                and().
                assertThat().body("success", equalTo(true)).
                and().
                assertThat().body("shuffled", equalTo(true)).
                and().
                assertThat().body("remaining", equalTo(52 - 2));

    }
}
