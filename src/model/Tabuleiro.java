package model;
import java.util.Random;


public class Tabuleiro {
	private final int linhas;
	private final int colunas;
	private final int numMinas;
	private Celula[][] celulas;

	public Tabuleiro(int linhas, int colunas, int numMinas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.numMinas = numMinas;
		this.celulas = new Celula[linhas][colunas];
		inicializar();
	}

	private void inicializar() {
		Random rand = new Random();

		// inicia o tabuleiro com todas as celulas vazias
		for(int i = 0; i < linhas; i++) {
			for(int j = 0; j < colunas; j++) {
				celulas[i][j] = new CelulaVazia(i, j);
			}
		}

		int minasColocadas = 0;
		// verifica se o numero de minas colocadas já alcançou o numero que o tabuleiro deve ter de minas
		// randomiza a posição das minas
		while(minasColocadas<numMinas) {
			int linha = rand.nextInt(linhas);
			int coluna = rand.nextInt(colunas);

			// se a posição gerada já for uma mina, ele gera uma nova posição
			// quando não é uma mina, é transformado em uma
			if (!(celulas[linha][coluna] instanceof CelulaComMina)) {
				celulas[linha][coluna] = new CelulaComMina(linha, coluna);
				minasColocadas++;
			}
		}

		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				if (celulas[i][j] instanceof CelulaVazia) {
					int minas = contarMinasAoRedor(i, j);
					((CelulaVazia) celulas[i][j]).setMinasAoRedor(minas);
				}
			}
		}
	}

	// percorre as 8 posições ao redor da célula central e conta quantas são minas
	// verifica os limites da matriz antes
	private int contarMinasAoRedor(int linha, int coluna) {
		int minas = 0;
		for (int i = linha - 1; i <= linha + 1; i++) {
			for (int j = coluna - 1; j <= coluna + 1; j++) {
				if (i >= 0 && i < linhas && j >= 0 && j < colunas) {
					if (celulas[i][j] instanceof CelulaComMina) {
						minas++;
					}
				}
			}
		}
		return minas;
	}

	// retorna a celula na posicição selecionada. Se a linha e coluna nao estiverem no tabuleiro
	// retorna null
	public Celula getCelula(int linha, int coluna) {
		if (linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas) {
			return celulas[linha][coluna];
		}
		return null;
	}
	//esse metodo revela a celula. Ele vai verificar se ela existe, se ja nao foi revelada
	//ou se nao esta com bandeira antes de chamar o metodo
	public void abrir(int linha, int coluna) {
		Celula celula = getCelula(linha, coluna); // recebe a celula na posição
		if (celula != null && !celula.estaRevelada() && !celula.estaMarcada()) {
			//verifica se a celula nao esta revelada, não esta marcada e se existe
			celula.revelar();
			if (celula instanceof CelulaVazia && ((CelulaVazia) celula).getMinasAoRedor() == 0) {
				//se a celula clicada for vazia e nao tiver vizinhas com minas ela vai
				//revelar todas as vazias proximas na mesma situação até encontrar uma
				//que possua vizinhas com minas
				revelarCelulasVaziasProximas(linha, coluna);
			}
		}
	}
	/**
	 * Esse metodo serve pra revelar as celulas proximas a uma celula que foi aberta e possuia 0 minas ao redor.
	 * Esse metodo mantém o ciclo até encontrar celulas com 1 ou mais celulas ao redor.
	 */
	private void revelarCelulasVaziasProximas(int linha, int coluna) {
		//vai percorrer as 8 posições vizinha(inclusive a celula que iniciou, que vai ser filtrada)
		for (int i = linha - 1; i <= linha + 1; i++) {
			for (int j = coluna - 1; j <= coluna + 1; j++) {
				//verifica se a posição está dentro dos limites do tabuleiro e não é a própria célula inicial.
				if (i >= 0 && i < linhas && j >= 0 && j < colunas && (i != linha || j != coluna)) {
					Celula vizinha = getCelula(i, j); //pega a celula vizinha
					if (vizinha != null && !vizinha.estaRevelada() && !vizinha.estaMarcada()) {
						// entra se a célula vizinha existe, não está revelada e não está marcada
						vizinha.revelar();
						//se a célula vizinha também for uma CelulaVazia com 0 minas ao redor,
						//chama o metodo para ela
						if (vizinha instanceof CelulaVazia && ((CelulaVazia) vizinha).getMinasAoRedor() == 0) {
							revelarCelulasVaziasProximas(i, j);
						}
					}
				}
			}
		}
	}

	/**
	 * Alterna o estado de marcação da bandeira de uma célula na posição especificada.
	 * Uma célula só pode ser alternada se não estiver revelada.
	 */

	public void alternarMarcacao(int linha, int coluna) {
		Celula celula = getCelula(linha, coluna); //pega a celula na posição
		if (celula != null && !celula.estaRevelada()) {
			//se a célula existe e não está revelada, alterna seu estado de marcação
			celula.alternarMarcacao();
		}
	}
}