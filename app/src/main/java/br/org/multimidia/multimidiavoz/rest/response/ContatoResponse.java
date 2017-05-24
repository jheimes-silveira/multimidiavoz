package br.org.multimidia.multimidiavoz.rest.response;

import java.util.List;

import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.domain.Meta;

public class ContatoResponse {

	private Meta meta;
	private List<Contato> contatos;
	
	public Meta getMeta() {
		if(meta == null) {
			meta = new Meta();
		}
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	public List<Contato> getContatos() {
		return contatos;
	}
	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
	
}
