package br.ufsc.setic;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TesteDeAceitacaoExemplo {

	private ChromeDriver seleniumComEsperaImplicita;
	private WebDriverWait seleniumComEsperaExplicita;
	private FluentWait<WebDriver> seleniumComEsperaFluente;

	@Before
	public void configurar() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");

		seleniumComEsperaImplicita = new ChromeDriver();
		seleniumComEsperaImplicita.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		seleniumComEsperaExplicita = new WebDriverWait(seleniumComEsperaImplicita, 5);

		seleniumComEsperaFluente = new FluentWait<>(seleniumComEsperaImplicita);
		seleniumComEsperaFluente.withTimeout(Duration.ofSeconds(10));
		seleniumComEsperaFluente.pollingEvery(Duration.ofSeconds(1));
		seleniumComEsperaFluente.ignoring(Exception.class);

		inicializarAplicacaoParaTeste();
		acessarMenuCadastrarBanco();
		cadastrarBanco();
		acessarMenuCadastrarAgencia();
		cadastrarAgencia();
	}

	@Test
	public void testar() throws Exception {
		assertEquals("Identificador", seleniumComEsperaImplicita.findElement(By.id("listarAgencias:rotuloIdentificador")).getText());
		assertEquals("Banco", seleniumComEsperaImplicita.findElement(By.id("listarAgencias:rotuloBanco")).getText());
		assertEquals("Nome", seleniumComEsperaImplicita.findElement(By.id("listarAgencias:rotuloNome")).getText());
		assertEquals("Banco do Brasil", seleniumComEsperaImplicita.findElement(By.id("listarAgencias:0:banco")).getText());
		assertEquals("Trindade", seleniumComEsperaImplicita.findElement(By.id("listarAgencias:0:nome")).getText());
	}

	private void inicializarAplicacaoParaTeste() {
		seleniumComEsperaImplicita.get("http://localhost:8080/projeto-de-teste");
		seleniumComEsperaImplicita.findElement(By.id("navegacao:limpar")).click();
	}

	private void acessarMenuCadastrarBanco() {
		WebElement menuBancos = seleniumComEsperaImplicita.findElement(By.id("navegacao:bancos"));
		new Actions(seleniumComEsperaImplicita).moveToElement(menuBancos).build().perform();
		seleniumComEsperaImplicita.findElement(By.id("navegacao:cadastrarBanco")).click();
		seleniumComEsperaExplicita.until(ExpectedConditions.invisibilityOfElementLocated(By.id("barraDeProgresso")));
	}

	private void cadastrarBanco() {
		seleniumComEsperaImplicita.findElement(By.id("cadastrarBanco:nome")).sendKeys("Banco do Brasil");
		seleniumComEsperaImplicita.findElement(By.xpath("//div[@id='cadastrarBanco:moeda']/div[1]/span")).click();
		seleniumComEsperaImplicita.findElement(By.id("cadastrarBanco:taxaDeTransacao_input")).sendKeys("1,50");
		seleniumComEsperaImplicita.findElement(By.id("cadastrarBanco:botaoCadastrar")).click();
		seleniumComEsperaExplicita.until(ExpectedConditions.textToBe(By.id("mensageiro_container"), "Banco cadastradado com sucesso!"));
	}

	private void acessarMenuCadastrarAgencia() {
		WebElement menuAgencias = seleniumComEsperaImplicita.findElement(By.id("navegacao:agencias"));
		new Actions(seleniumComEsperaImplicita).moveToElement(menuAgencias).build().perform();
		seleniumComEsperaImplicita.findElement(By.id("navegacao:cadastrarAgencia")).click();
		seleniumComEsperaExplicita.until(ExpectedConditions.invisibilityOfElementLocated(By.id("barraDeProgresso")));
	}

	private void cadastrarAgencia() {
		WebElement selectBancosInternoDoPrimeFaces = seleniumComEsperaImplicita.findElement(By.id("cadastrarAgencia:banco_input"));
		new Select(selectBancosInternoDoPrimeFaces).selectByIndex(1);
		seleniumComEsperaImplicita.findElement(By.id("cadastrarAgencia:nome")).sendKeys("Trindade");
		seleniumComEsperaImplicita.findElement(By.id("cadastrarAgencia:botaoCadastrar")).click();
		seleniumComEsperaFluente.until(driver -> {
			return driver.findElement(By.id("mensageiro_container")).getText().equals("Agência cadastrada com sucesso!");
		});
	}

	@After
	public void finalizar() throws Exception {
		Thread.sleep(10000);
		seleniumComEsperaImplicita.close();
	}

}
