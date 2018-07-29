package br.ufsc.setic.modelo.moeda;

public class FabricaDeDinheiro {

	private Moeda moeda;

	public FabricaDeDinheiro(Moeda moeda) {
		this.moeda = moeda;
	}

	public Dinheiro construir() {
		return construir(0, 0);
	}

	public Dinheiro construir(Integer inteiro) {
		return construir(inteiro, 0);
	}

	public Dinheiro construir(Integer inteiro, Integer fracao) {
		garantirApenasPositivos(inteiro, fracao);
		Integer base = moeda.getBase();
		Integer soma = inteiro * base + fracao;
		return new Dinheiro(moeda, soma / base, soma % base);
	}

	private void garantirApenasPositivos(Integer inteiro, Integer fracao) {
		if (inteiro < 0 || fracao < 0) {
			throw new IllegalArgumentException();
		}
	}

}
