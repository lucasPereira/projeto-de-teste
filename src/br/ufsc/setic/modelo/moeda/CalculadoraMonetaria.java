package br.ufsc.setic.modelo.moeda;

public class CalculadoraMonetaria {

	private Integer valor;
	private Moeda moeda;
	private ConversorDeMoeda conversor;

	public CalculadoraMonetaria(Moeda moeda, ConversorDeMoeda conversor) {
		this.valor = 0;
		this.moeda = moeda;
		this.conversor = conversor;
	}

	public CalculadoraMonetaria somar(Dinheiro quantia) {
		Dinheiro convertido = conversor.converter(quantia, moeda);
		valor += convertido.getInteiro() * moeda.getBase();
		valor += convertido.getFracao();
		return this;
	}

	public CalculadoraMonetaria subtrair(Dinheiro quantia) {
		Dinheiro convertido = conversor.converter(quantia, moeda);
		valor -= convertido.getInteiro() * moeda.getBase();
		valor -= convertido.getFracao();
		return this;
	}

	public ValorMonetario calcular() {
		Integer inteiro = valor / moeda.getBase();
		Integer fracionado = valor % moeda.getBase();
		Dinheiro quantia = new Dinheiro(moeda, Math.abs(inteiro), Math.abs(fracionado));
		Boolean sinal = valor >= 0;
		return new ValorMonetario(sinal, quantia);
	}

}
