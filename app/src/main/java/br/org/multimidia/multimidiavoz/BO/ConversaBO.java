package br.org.multimidia.multimidiavoz.BO;

import android.content.Context;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.org.multimidia.multimidiavoz.db.GenericCRUD;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.domain.Conversa;

/**
 * Created by jheimes on 04/04/17.
 */

public class ConversaBO extends GenericCRUD<Conversa, Integer> {

    public ConversaBO(Context context) throws SQLException {
        super(context, Conversa.class, Contato.class.getSimpleName());
    }

    public List<Conversa> findAll() {
        try {
            QueryBuilder<Conversa, Integer> queryBuilder = queryBuilder();
            queryBuilder.orderBy("dtaFinal", false);

            PreparedQuery<Conversa> preparedQuery = queryBuilder.prepare();
            List<Conversa> list = query(preparedQuery);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
