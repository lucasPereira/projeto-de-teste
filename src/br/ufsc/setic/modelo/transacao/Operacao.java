package br.ufsc.setic.modelo.transacao;

public class Operacao {

	private EstadosDeOperacao estado;

	public Operacao(EstadosDeOperacao estado, Transacao... transacoes) {
		this.estado = estado;
	}

	public EstadosDeOperacao obterEstado() {
		return estado;
	}

}
