package br.ufsc.setic.modelo.transacao;

import br.ufsc.setic.modelo.moeda.CalculadoraMonetaria;

public interface Transacao {

	public void contabilizar(CalculadoraMonetaria calculadora);

}
