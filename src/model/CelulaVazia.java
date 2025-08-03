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

    @Override
    public void revelar() {
        this.setRevelada(true);
    }

    @Override
    public boolean temMina(){
        return false;
    }
}
