package br.ufsc.setic.modelo;

public class Agencia {

	private Integer codigo;
	private String nome;
	private Banco banco;

	protected Agencia(Integer codigo, String nome, Banco banco) {
		this.codigo = codigo;
		this.nome = nome;
		this.banco = banco;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public Integer getDigitoVerificador() {
		return codigo % 10;
	}

	public String getNome() {
		return nome;
	}

	public Banco getBanco() {
		return banco;
	}

	@Override
	public String toString() {
		return String.format("%03d-%d", codigo, getDigitoVerificador());
	}

}
