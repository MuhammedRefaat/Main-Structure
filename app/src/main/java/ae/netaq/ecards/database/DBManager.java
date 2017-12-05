package ae.netaq.ecards.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ae.netaq.ecards.database.dataModel.Cards;

/**
 * Created by M.Refaat on 12/4/2017.
 */
public class DBManager extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "mnawarah.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Cards, Integer> cardsDao;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void close() {
        super.close();
        cardsDao = null;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, Cards.class);
        } catch (SQLException e) {
            Log.e(DBManager.class.getName(), "Unable to create databases", e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {

    }

    public Dao<Cards, Integer> getCardsDao() throws SQLException {

        if (cardsDao == null) {
            cardsDao = getDao(Cards.class);
        }

        return cardsDao;
    }

    /**
     * This method is used to entirely clear the DB from any saved records in all the tables
     */
    public void clearTheDB(boolean logout) {

        // NOTE : I didn't use "TableUtils.clearTable" as the ORMLite library author advising not to use it
        // and use Drop & Create instead for some auto-increment issues he is not sure of
        try {
            // first drop all of the tables
            TableUtils.dropTable(connectionSource, Cards.class, true);

            // then create all of them again
            TableUtils.createTable(connectionSource, Cards.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
