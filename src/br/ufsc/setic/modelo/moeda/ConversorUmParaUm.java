package br.ufsc.setic.modelo.moeda;

public class ConversorUmParaUm implements ConversorDeMoeda {

	@Override
	public Dinheiro converter(Dinheiro valor, Moeda destino) {
		return new FabricaDeDinheiro().construir(destino, valor.getInteiro(), valor.getFracao());
	}

}
