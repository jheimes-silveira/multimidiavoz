package br.org.multimidia.multimidiavoz.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.ui.adapter.AdapterLvContatos;

public class ActPesquisaContato extends AppCompatActivity {

    private Context context;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fgt_contatos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    /**
     * inicializa todos meus componentes em tela
     */
    private void initComponents() {
        initDados();
        initR();
        onLvContatos();
    }

    private void initDados() {
        context = ActPesquisaContato.this;
    }

    private void onLvContatos() {
        List<Contato> profiles = new ArrayList<>();
        for(int i = 0 ; i < 9 ; i++) {
            Contato user = new Contato();
            user.setNome("Nome User"+ (i+1));
            user.setNumero("(34) 9 9226434"+ (i+1));
            profiles.add(user);
        }
        listView.setAdapter(new AdapterLvContatos(context, profiles));
    }

    private void initR() {
        listView = (ListView) findViewById(R.id.listview);
    }
}
