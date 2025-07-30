package model;

public class CelulaComMina extends Celula{
    public CelulaComMina(int linha, int coluna) {
        super(linha, coluna);
    }
    
    @Override
    public void revelar() {
    	this.revelada = true;
    	System.out.print("Caiu em uma bomba!");
    }
}
