package br.ufsc.setic.modelo.moeda;

public class FabricaDeDinheiro {

	public Dinheiro zero(Moeda moeda) {
		return construir(moeda, 0, 0);
	}

	public Dinheiro construir(Moeda moeda, Integer inteiro, Integer fracao) {
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
