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
}
