package br.ufsc.setic.modelo.moeda;

public final class ValorMonetario {

	private Boolean positivo;
	private Dinheiro quantia;

	public ValorMonetario(Boolean positivo, Dinheiro quantia) {
		this.positivo = positivo;
		this.quantia = quantia;
	}

	public Boolean getPositivo() {
		return positivo;
	}

	public Dinheiro getQuantia() {
		return quantia;
	}

	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof ValorMonetario) {
			ValorMonetario outro = (ValorMonetario) objeto;
			Boolean mesmoSinal = positivo.equals(outro.positivo);
			Boolean mesmaQuantia = quantia.equals(outro.quantia);
			return mesmoSinal && mesmaQuantia;
		}
		return super.equals(objeto);
	}

	@Override
	public String toString() {
		return String.format("(%s) %s", positivo ? "+" : "-", quantia);
	}

}
