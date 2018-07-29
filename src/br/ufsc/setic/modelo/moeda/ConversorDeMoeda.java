package br.ufsc.setic.modelo.moeda;

public interface ConversorDeMoeda {

	public Dinheiro converter(Dinheiro valor, Moeda destino);

}
