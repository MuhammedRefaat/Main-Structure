package ae.netaq.ecards.database.dataModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by M.Refaat on 12/4/2017.
 */

@DatabaseTable(tableName = "cards")
public class Cards {

    @DatabaseField(id = true, unique = true)
    private String cardId;

    @DatabaseField
    private String cardTitle;

    @DatabaseField
    private String cardUrl;

    @DatabaseField
    private String cardRating;

    @DatabaseField
    private int numOfCustomizations;

    @DatabaseField
    private boolean isMyCard;


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardUrl() {
        return cardUrl;
    }

    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }

    public String getCardRating() {
        return cardRating;
    }

    public void setCardRating(String cardRating) {
        this.cardRating = cardRating;
    }

    public int getNumOfCustomizations() {
        return numOfCustomizations;
    }

    public void setNumOfCustomizations(int numOfCustomizations) {
        this.numOfCustomizations = numOfCustomizations;
    }

    public boolean isMyCard() {
        return isMyCard;
    }

    public void setMyCard(boolean myCard) {
        isMyCard = myCard;
    }
}
