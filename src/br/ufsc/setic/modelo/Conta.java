package br.ufsc.setic.modelo;

public class Conta {

	private Integer codigo;
	private String titular;
	private Agencia agencia;

	protected Conta(Integer codigo, String titular, Agencia agencia) {
		this.codigo = codigo;
		this.titular = titular;
		this.agencia = agencia;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public Integer getDigitoVerificador() {
		return (titular.length() + codigo) % 10;
	}

	public String getTitular() {
		return titular;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	@Override
	public String toString() {
		return String.format("%04d-%d", codigo, getDigitoVerificador());
	}

}
