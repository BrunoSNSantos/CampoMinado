package model;

public abstract class Celula {
	protected int linha;
	protected int coluna;
	protected boolean revelada;
	protected boolean marcada; // Adicionado para a bandeira

	// Construtor responsável por inicializar a celula e definir sua posição inical
	public Celula(int linha, int coluna) {
		this.linha= linha;
		this.coluna = coluna;
		this.revelada = false;
		this.marcada = false; // Inicializa como não marcada
	}

	public boolean estaRevelada() {
		return revelada;
	}

	public void setRevelada(boolean revelada) {
		this.revelada = revelada;
	}

	public boolean estaMarcada() {
		return marcada;
	}

	public void alternarMarcacao() {
		//alterna o estado da bandeira
		if (!revelada) {
			//só pode alternada se não estiver revelada
			this.marcada = !this.marcada;
		}
	}

	public int getLinha() {
		//não é necessario, pois a linha já é um campo da classe
		return linha;
	}

	public int getColuna() {
		//não é necessario, pois a coluna já é um campo da classe
		return coluna;
	}

	//metodo abstrato para revelar a celula, é abstrato, pois seu funcionamento será diferente dependendo da célula
	public abstract void revelar();

}