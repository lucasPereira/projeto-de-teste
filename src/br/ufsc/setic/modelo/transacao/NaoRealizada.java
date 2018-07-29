package br.ufsc.setic.modelo.transacao;

import javax.persistence.Entity;

import br.ufsc.setic.modelo.moeda.CalculadoraMonetaria;

@Entity
public class NaoRealizada extends Transacao {

	private Class<?> tipo;

	public NaoRealizada() {
		super();
	}

	public NaoRealizada(Transacao transacao) {
		super(transacao.getConta());
		this.tipo = transacao.getClass();
	}

	public Class<?> getTipo() {
		return tipo;
	}

	@Override
	public void contabilizar(CalculadoraMonetaria calculadora) {}

}
