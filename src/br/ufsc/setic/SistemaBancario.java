package br.ufsc.setic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.ufsc.setic.modelo.Agencia;
import br.ufsc.setic.modelo.Banco;
import br.ufsc.setic.modelo.Conta;
import br.ufsc.setic.modelo.moeda.CalculadoraMonetaria;
import br.ufsc.setic.modelo.moeda.ConversorDeMoedaUmParaUm;
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

	private FabricaDeDinheiro fabricaDeDinheiro;
	private ConversorDeMoedaUmParaUm conversorDeMoeda;
	private EntityManagerFactory fabricaDeGerenciadorDeEntidade;

	public SistemaBancario() {
		fabricaDeDinheiro = new FabricaDeDinheiro(Moeda.BRL);
		conversorDeMoeda = new ConversorDeMoedaUmParaUm();
		fabricaDeGerenciadorDeEntidade = Persistence.createEntityManagerFactory("teste");
	}

	public Banco criarBanco(String nome, Moeda moeda) {
		Dinheiro taxaDeTransacao = fabricaDeDinheiro.construir();
		return new Banco(nome, moeda, taxaDeTransacao);
	}

	public Agencia criarAgencia(String nome, Banco banco) {
		return new Agencia(nome, banco);
	}

	public Conta criarConta(String titular, Agencia agencia) {
		return new Conta(titular, agencia);
	}

	public void cadastrarBanco(Banco banco) {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		gerenciadorDeEntidade.getTransaction().begin();
		gerenciadorDeEntidade.persist(banco);
		gerenciadorDeEntidade.getTransaction().commit();
		gerenciadorDeEntidade.close();
	}

	public void removerBanco(Banco banco) {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		gerenciadorDeEntidade.getTransaction().begin();
		gerenciadorDeEntidade.remove(banco);
		gerenciadorDeEntidade.getTransaction().commit();
		gerenciadorDeEntidade.close();
	}

	public List<Banco> listarBancos() {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		List<Banco> bancos = gerenciadorDeEntidade.createQuery("SELECT banco FROM Banco banco ORDER BY banco.nome", Banco.class).getResultList();
		gerenciadorDeEntidade.close();
		return bancos;
	}

	public void cadastrarAgencia(Agencia agencia) {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		gerenciadorDeEntidade.getTransaction().begin();
		gerenciadorDeEntidade.persist(agencia);
		gerenciadorDeEntidade.getTransaction().commit();
		gerenciadorDeEntidade.close();
	}

	public List<Agencia> listarAgencias() {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		List<Agencia> agencias = gerenciadorDeEntidade.createQuery("SELECT agencia FROM Agencia agencia ORDER BY agencia.nome, agencia.banco.nome", Agencia.class).getResultList();
		gerenciadorDeEntidade.close();
		return agencias;
	}

	public void cadastrarConta(Conta conta) {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		gerenciadorDeEntidade.getTransaction().begin();
		gerenciadorDeEntidade.persist(conta);
		gerenciadorDeEntidade.getTransaction().commit();
		gerenciadorDeEntidade.close();
	}

	public List<Conta> listarContas() {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		List<Conta> contas = gerenciadorDeEntidade.createQuery("SELECT conta FROM Conta conta ORDER BY conta.titular, conta.agencia.nome, conta.agencia.banco.nome", Conta.class).getResultList();
		gerenciadorDeEntidade.close();
		return contas;
	}

	public void cadastrarTransacao(Transacao transacao) {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		gerenciadorDeEntidade.getTransaction().begin();
		gerenciadorDeEntidade.persist(transacao);
		gerenciadorDeEntidade.getTransaction().commit();
		gerenciadorDeEntidade.close();
	}

	public List<Transacao> listarTransacoes(Conta conta) {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		List<Transacao> transacoes = gerenciadorDeEntidade.createQuery("SELECT transacao FROM Transacao transacao WHERE transacao.conta = :conta", Transacao.class).setParameter("conta", conta).getResultList();
		gerenciadorDeEntidade.close();
		return transacoes;
	}

	public ValorMonetario calcularSaldo(Conta conta) {
		Moeda moeda = conta.getAgencia().getBanco().getMoeda();
		CalculadoraMonetaria calculadora = new CalculadoraMonetaria(moeda, conversorDeMoeda);
		List<Transacao> transacoes = listarTransacoes(conta);
		for (Transacao transacao : transacoes) {
			transacao.contabilizar(calculadora);
		}
		return calculadora.calcular();
	}

	public Operacao depositar(Conta conta, Dinheiro quantia) {
		Transacao entrada = new Entrada(conta, quantia);
		EstadosDeOperacao estado = EstadosDeOperacao.SUCESSO;
		Boolean moedaInvalida = !conta.getAgencia().getBanco().getMoeda().equals(quantia.getMoeda());
		if (moedaInvalida) {
			entrada = new NaoRealizada(entrada);
			estado = EstadosDeOperacao.MOEDA_INVALIDA;
		}
		cadastrarTransacao(entrada);
		return new Operacao(estado, entrada);
	}

	public Operacao sacar(Conta conta, Dinheiro quantia) {
		Transacao saida = new Saida(conta, quantia);
		EstadosDeOperacao estado = EstadosDeOperacao.SUCESSO;
		Moeda moeda = conta.getAgencia().getBanco().getMoeda();
		CalculadoraMonetaria saldo = new CalculadoraMonetaria(moeda, conversorDeMoeda);
		List<Transacao> transacoes = listarTransacoes(conta);
		for (Transacao transacao : transacoes) {
			transacao.contabilizar(saldo);
		}
		Boolean saldoJaEstaNegativo = !saldo.calcular().getPositivo();
		Boolean saldoFicaraNegativo = !saldo.subtrair(quantia).calcular().getPositivo();
		if (saldoJaEstaNegativo || saldoFicaraNegativo) {
			saida = new NaoRealizada(saida);
			estado = EstadosDeOperacao.SALDO_INSUFICIENTE;
		}
		Boolean moedaInvalida = !conta.getAgencia().getBanco().getMoeda().equals(quantia.getMoeda());
		if (moedaInvalida) {
			saida = new NaoRealizada(saida);
			estado = EstadosDeOperacao.MOEDA_INVALIDA;
		}
		cadastrarTransacao(saida);
		return new Operacao(estado, saida);
	}

	public Operacao transferir(Conta origem, Conta destino, Dinheiro quantia) {
		Transacao saida = new Saida(origem, quantia);
		Transacao entrada = new Entrada(destino, quantia);
		EstadosDeOperacao estado = EstadosDeOperacao.SUCESSO;
		CalculadoraMonetaria saldoOrigem = new CalculadoraMonetaria(quantia.getMoeda(), conversorDeMoeda);
		List<Transacao> transacoes = listarTransacoes(origem);
		for (Transacao transacao : transacoes) {
			transacao.contabilizar(saldoOrigem);
		}
		Boolean saldoJaEstaNegativo = !saldoOrigem.calcular().getPositivo();
		Boolean saldoFicaraNegativo = !saldoOrigem.subtrair(quantia).calcular().getPositivo();
		if (saldoJaEstaNegativo || saldoFicaraNegativo) {
			saida = new NaoRealizada(saida);
			entrada = new NaoRealizada(entrada);
			estado = EstadosDeOperacao.SALDO_INSUFICIENTE;
		}
		Boolean moedaOrigemInvalida = !origem.getAgencia().getBanco().getMoeda().equals(quantia.getMoeda());
		Boolean moedaDestinoInvalida = !destino.getAgencia().getBanco().getMoeda().equals(quantia.getMoeda());
		if (moedaOrigemInvalida || moedaDestinoInvalida) {
			saida = new NaoRealizada(saida);
			entrada = new NaoRealizada(entrada);
			estado = EstadosDeOperacao.MOEDA_INVALIDA;
		}
		cadastrarTransacao(entrada);
		cadastrarTransacao(saida);
		return new Operacao(estado, saida, entrada);
	}

	public void limparBancoDeDados() {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		gerenciadorDeEntidade.getTransaction().begin();
		gerenciadorDeEntidade.createQuery("DELETE FROM Transacao").executeUpdate();
		gerenciadorDeEntidade.createQuery("DELETE FROM Entrada").executeUpdate();
		gerenciadorDeEntidade.createQuery("DELETE FROM Saida").executeUpdate();
		gerenciadorDeEntidade.createQuery("DELETE FROM NaoRealizada").executeUpdate();
		gerenciadorDeEntidade.createQuery("DELETE FROM Conta").executeUpdate();
		gerenciadorDeEntidade.createQuery("DELETE FROM Agencia").executeUpdate();
		gerenciadorDeEntidade.createQuery("DELETE FROM Banco").executeUpdate();
		gerenciadorDeEntidade.getTransaction().commit();
		gerenciadorDeEntidade.close();
	}

	public void remover(Object entidade) {
		EntityManager gerenciadorDeEntidade = fabricaDeGerenciadorDeEntidade.createEntityManager();
		gerenciadorDeEntidade.getTransaction().begin();
		gerenciadorDeEntidade.remove(entidade);
		gerenciadorDeEntidade.getTransaction().commit();
		gerenciadorDeEntidade.close();
	}

}
