package br.ufsc.setic.modelo.moeda;

public enum Moeda {

	BRL("R$", 100), USD("$", 100), EUR("€", 100);

	private String simbolo;
	private Integer base;

	private Moeda(String simbolo, Integer base) {
		this.simbolo = simbolo;
		this.base = base;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public Integer getBase() {
		return base;
	}

}
