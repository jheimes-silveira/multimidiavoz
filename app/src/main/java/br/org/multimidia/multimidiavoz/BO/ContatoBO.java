package br.org.multimidia.multimidiavoz.BO;

import android.content.Context;

import java.sql.SQLException;

import br.org.multimidia.multimidiavoz.db.GenericCRUD;
import br.org.multimidia.multimidiavoz.domain.Contato;

/**
 * Created by jheimes on 04/04/17.
 */

public class ContatoBO extends GenericCRUD<Contato, Integer> {

    public ContatoBO(Context context) throws SQLException {
        super(context, Contato.class, Contato.class.getSimpleName());
    }
}
