package br.edu.fametro;

public class Aliquota {
    private Double inicioFaixa;
    private Double fimFaixa;
    private Double aliquota;
    private Double deducao;

    public Double getInicioFaixa() {
        return inicioFaixa;
    }

    public void setInicioFaixa(Double inicioFaixa) {
        this.inicioFaixa = inicioFaixa;
    }

    public Double getFimFaixa() {
        return fimFaixa;
    }

    public void setFimFaixa(Double fimFaixa) {
        this.fimFaixa = fimFaixa;
    }

    public Double getAliquota() {
        return aliquota;
    }

    public void setAliquota(Double aliquota) {
        this.aliquota = aliquota;
    }

    public Double getDeducao() {
        return deducao;
    }

    public void setDeducao(Double deducao) {
        this.deducao = deducao;
    }

    public Aliquota(Double inicioFaixa, Double fimFaixa, Double aliquota, Double deducao) {
        this.inicioFaixa = inicioFaixa;
        this.fimFaixa = fimFaixa;
        this.aliquota = aliquota;
        this.deducao = deducao;
    }
}
