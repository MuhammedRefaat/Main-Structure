package ae.netaq.ecards.controllers;


import java.sql.SQLException;

import ae.netaq.ecards.database.DBManager;
import ae.netaq.ecards.database.dataModel.Cards;

/**
 * Created by M.Refaat on 12/7/2017.
 */

public class DatabaseController {

    DBManager dbManager;

    public DatabaseController(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * The method which is responsible for retrieving the corresponding card data from the
     * Databas using the card id
     *
     * @param cardId the id of the corresponding card
     * @return the corresponding card
     */
    public Cards getCard(String cardId) {
        Cards card = new Cards();
        try {
            card = dbManager.getCardsDao().queryForEq(Cards.CARD_ID, cardId).get(0);
        } catch (SQLException ex) {
            card.setCardId(cardId);
            ex.printStackTrace();
        }
        return card;
    }

    /**
     * to delete a card from the database
     *
     * @param cardId the corresponding card|Id to be deleted
     * @return if the card is Successfully deleted or NOT
     */
    public boolean deleteCard(String cardId) {
        try {
            Cards card = dbManager.getCardsDao().queryForEq(Cards.CARD_ID, cardId).get(0);
            dbManager.getCardsDao().delete(card);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * to add/remove from/to MyCards
     *
     * @param cardId the corresponding cardId to be added/removed from/to MyCards
     * @return if the operation is Successfully Done or NOT
     */
    public boolean addOrRemoveFromMyCards(String cardId, boolean add) {
        try {
            Cards card = dbManager.getCardsDao().queryForEq(Cards.CARD_ID, cardId).get(0);
            card.setMyCard(add);
            dbManager.getCardsDao().update(card);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
