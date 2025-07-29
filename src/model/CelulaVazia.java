package model;

public class CelulaVazia extends Celula {
	private int minasAoRedor;
	
    public CelulaVazia(int linha, int coluna) {
        super(linha, coluna);
        this.minasAoRedor = 0;
    }

    public void setMinasAoRedor(int minas) {
    	this.minasAoRedor = minas;
    }
    
    public int getMinasAoRedor() {
    	return minasAoRedor;
    }
    
    public void revelar() {
    	this.revelada=true;
    	System.out.print("Mina vazia e minas ao redor: " + minasAoRedor);
    }
}
