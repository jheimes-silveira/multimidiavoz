package br.org.multimidia.multimidiavoz.domain;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jheimes on 20/02/17.
 */

public class Contato {

    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String SENHA = "senha";
    public static final String NUMERO = "numero";
    public static final String CONTATO = "contato";
    public static final String CONTATOS = "contatos";
    public static final String CONTATO_LISTA_TELEFONICA = "contatoListaTelefonica";
    public static final String MEU_NUMERO = "meuNumero";
    public static final String CONTATO_NUMERO = "contatoNumero";

    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @DatabaseField(columnName = "nome")
    private String nome;

    @DatabaseField(columnName = "numero")
    private String numero;

    @DatabaseField(columnName = "imagem")
    private String imagem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
