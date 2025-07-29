package model;

public abstract class Celula {
	protected int linha;
	protected int coluna;
	protected boolean revelada;
	
	// Construtor responsável por inicializar a celula e definir sua posição inical
	public Celula(int linha, int coluna) {
		this.linha= linha;
		this.coluna = coluna;
		this.revelada = false;
	}
	
	public boolean estaRevelada() {
        return revelada;
    }
	
	public void setRevelada(boolean revelada) {
		this.revelada = revelada;
	}
	
	public int getLinha(int linha) {
		return linha;
	}
	
	public int getColuna(int coluna) {
		return coluna;
	}
	
	//metodo abstrato para revelar a celula, é abstrato, pois seu funcionamento será diferente dependendo da célula
	public abstract void revelar();
	
}
