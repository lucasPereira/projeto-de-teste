package br.ufsc.setic.modelo;

import java.util.LinkedList;
import java.util.List;

import br.ufsc.setic.modelo.moeda.CalculadoraMonetaria;
import br.ufsc.setic.modelo.moeda.ConversorUmParaUm;
import br.ufsc.setic.modelo.moeda.Dinheiro;
import br.ufsc.setic.modelo.moeda.FabricaDeDinheiro;
import br.ufsc.setic.modelo.moeda.Moeda;
import br.ufsc.setic.modelo.moeda.ValorMonetario;
import br.ufsc.setic.modelo.transacao.Entrada;
import br.ufsc.setic.modelo.transacao.EstadosDeOperacao;
import br.ufsc.setic.modelo.transacao.NaoRealizada;
import br.ufsc.setic.modelo.transacao.Operacao;
import br.ufsc.setic.modelo.transacao.Saida;
import br.ufsc.setic.modelo.transacao.Transacao;

public class SistemaBancario {

	private FabricaDeDinheiro fabrica;
	private ConversorUmParaUm conversor;

	public SistemaBancario() {
		fabrica = new FabricaDeDinheiro();
		conversor = new ConversorUmParaUm();
	}

	public Banco criarBanco(String nome, Moeda moeda) {
		Integer codigo = 0;
		return new Banco(codigo, nome, moeda, fabrica.zero(moeda));
	}

	public Agencia criarAgencia(String nome, Banco banco) {
		Integer codigo = 0;
		return new Agencia(codigo, nome, banco);
	}

	public Conta criarConta(String titular, Agencia agencia) {
		Integer codigo = 0;
		return new Conta(codigo, titular, agencia);
	}

	public ValorMonetario calcularSaldo(Conta conta) {
		CalculadoraMonetaria calculadora = contabilizarSaldo(conta);
		return calculadora.calcular();
	}

	public Operacao depositar(Conta conta, Dinheiro quantia) {
		Transacao entrada = new Entrada(conta, quantia);
		EstadosDeOperacao estado = EstadosDeOperacao.SUCESSO;
		if (moedaInvalida(conta, quantia)) {
			entrada = new NaoRealizada(entrada);
			estado = EstadosDeOperacao.MOEDA_INVALIDA;
		}
		return new Operacao(estado, entrada);
	}

	public Operacao sacar(Conta conta, Dinheiro quantia) {
		Transacao saida = new Saida(conta, quantia);
		EstadosDeOperacao estado = EstadosDeOperacao.SUCESSO;
		CalculadoraMonetaria saldo = contabilizarSaldo(conta);
		Boolean saldoJaEstaNegativo = !saldo.calcular().getEstaPositivo();
		Boolean saldoFicaraNegativo = !saldo.subtrair(quantia).calcular().getEstaPositivo();
		if (saldoJaEstaNegativo || saldoFicaraNegativo) {
			saida = new NaoRealizada(saida);
			estado = EstadosDeOperacao.SALDO_INSUFICIENTE;
		}
		if (moedaInvalida(conta, quantia)) {
			saida = new NaoRealizada(saida);
			estado = EstadosDeOperacao.MOEDA_INVALIDA;
		}
		return new Operacao(estado, saida);
	}

	public Operacao transferir(Conta origem, Conta destino, Dinheiro quantia) {
		Transacao saida = new Saida(origem, quantia);
		Transacao entrada = new Entrada(destino, quantia);
		EstadosDeOperacao estado = EstadosDeOperacao.SUCESSO;
		CalculadoraMonetaria saldoOrigem = contabilizarSaldo(origem);
		Boolean saldoJaEstaNegativo = !saldoOrigem.calcular().getEstaPositivo();
		Boolean saldoFicaraNegativo = !saldoOrigem.subtrair(quantia).calcular().getEstaPositivo();
		if (saldoJaEstaNegativo || saldoFicaraNegativo) {
			saida = new NaoRealizada(saida);
			estado = EstadosDeOperacao.SALDO_INSUFICIENTE;
		}
		if (moedaInvalida(origem, quantia) || moedaInvalida(destino, quantia)) {
			saida = new NaoRealizada(saida);
			entrada = new NaoRealizada(entrada);
			estado = EstadosDeOperacao.MOEDA_INVALIDA;
		}
		return new Operacao(estado, saida, entrada);
	}

	private Boolean moedaInvalida(Conta conta, Dinheiro quantia) {
		Moeda moedaDoBanco = conta.getAgencia().getBanco().getMoeda();
		Moeda moedaDaOperacao = quantia.getMoeda();
		return !moedaDoBanco.equals(moedaDaOperacao);
	}

	private CalculadoraMonetaria contabilizarSaldo(Conta conta) {
		Moeda moeda = conta.getAgencia().getBanco().getMoeda();
		CalculadoraMonetaria calculadora = new CalculadoraMonetaria(moeda, conversor);
		List<Transacao> transacoes = new LinkedList<>();
		for (Transacao transacao : transacoes) {
			transacao.contabilizar(calculadora);
		}
		return calculadora;
	}

}
