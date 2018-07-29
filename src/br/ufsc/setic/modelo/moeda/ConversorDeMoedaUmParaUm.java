package br.ufsc.setic.modelo.moeda;

public class ConversorDeMoedaUmParaUm implements ConversorDeMoeda {

	@Override
	public Dinheiro converter(Dinheiro valor, Moeda destino) {
		FabricaDeDinheiro fabricaDeDinheiro = new FabricaDeDinheiro(destino);
		return fabricaDeDinheiro.construir(valor.getInteiro(), valor.getFracao());
	}

}
