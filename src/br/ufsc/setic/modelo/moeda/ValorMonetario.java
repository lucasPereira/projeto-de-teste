package br.ufsc.setic.modelo.moeda;

public final class ValorMonetario {

	private Boolean estaPositivo;
	private Dinheiro quantia;

	protected ValorMonetario(Boolean estaPositivo, Dinheiro quantia) {
		this.estaPositivo = estaPositivo;
		this.quantia = quantia;
	}

	public Boolean getEstaPositivo() {
		return estaPositivo;
	}

	public Dinheiro getQuantia() {
		return quantia;
	}

	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof ValorMonetario) {
			ValorMonetario outro = (ValorMonetario) objeto;
			Boolean mesmoSinal = estaPositivo.equals(outro.estaPositivo);
			Boolean mesmaQuantia = quantia.equals(outro.quantia);
			return mesmoSinal && mesmaQuantia;
		}
		return super.equals(objeto);
	}

	@Override
	public String toString() {
		return String.format("(%s) %s", estaPositivo ? "+" : "-", quantia);
	}

}
