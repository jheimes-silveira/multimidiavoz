package br.org.multimidia.multimidiavoz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.domain.Conversa;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String databaseName = "multimidiavoz.db";
    private static final int databaseVersion = 1;

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sd, ConnectionSource cs) {
        try {
            TableUtils.createTable(cs, Contato.class);
            TableUtils.createTable(cs, Conversa.class);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sd, ConnectionSource cs, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(cs, Contato.class, true);
            TableUtils.dropTable(cs, Conversa.class, true);
            onCreate(sd, cs);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(){
        super.close();
    }
}
