package br.ufsc.setic.modelo.transacao;

import br.ufsc.setic.modelo.moeda.CalculadoraMonetaria;

public class NaoRealizada implements Transacao {

	private Transacao transacao;

	public NaoRealizada(Transacao transacao) {
		this.transacao = transacao;
	}

	public Transacao getTransacao() {
		return transacao;
	}

	@Override
	public void contabilizar(CalculadoraMonetaria calculadora) {}

}
