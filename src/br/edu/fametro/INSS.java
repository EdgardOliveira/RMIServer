package br.edu.fametro;

import java.util.ArrayList;
import java.util.List;

import static br.edu.fametro.Rotinas.calcularPercentual;

public class INSS {
    //declaração de variáveis
    private double deducaoINSS;
    private double teto = 751.99;
    private double faixa;
    private double inss;
    private List<Aliquota> faixas = new ArrayList<>();

    public double getDeducaoINSS() {
        return deducaoINSS;
    }

    public double getFaixa() {
        return faixa;
    }

    public double getINSS() {
        return inss;
    }

    //construtor vazio
    public INSS() {
        configurarTabela();
    }

    //construtor com parâmetros
    public INSS(double salarioBase) {
        configurarTabela();
        calcularFaixa(salarioBase);
        calcularINSS(salarioBase, faixa);
    }

    private void configurarTabela(){
        faixas.add(new Aliquota(0.0, 1100.0, 7.5, 0.0));
        faixas.add(new Aliquota(1100.01, 2203.48, 9.0, 16.5));
        faixas.add(new Aliquota(2203.49, 3305.22, 12.0, 82.604));
        faixas.add(new Aliquota(3305.23, 6433.57, 14.0, 148.708));
    }

    public void calcularFaixa(double salarioBase) {
        for (Aliquota aliquota : faixas)
            if ((salarioBase >= aliquota.getInicioFaixa()) && (salarioBase <= aliquota.getFimFaixa()))
                faixa = aliquota.getAliquota();
    }

    public void calcularINSS(double salarioBase, double faixa) {
        if ((salarioBase >= faixas.get(0).getInicioFaixa()) && (salarioBase <= faixas.get(0).getFimFaixa())) {
            inss = calcularPercentual(salarioBase, faixa);
        }else if ((salarioBase >= faixas.get(1).getInicioFaixa()) && (salarioBase <= faixas.get(1).getFimFaixa())) {
            inss = calcularPercentual(salarioBase - faixas.get(0).getFimFaixa(), faixa);
            inss += calcularPercentual(faixas.get(0).getFimFaixa(), faixas.get(0).getAliquota());
        }else if ((salarioBase >= faixas.get(2).getInicioFaixa()) && (salarioBase <= faixas.get(2).getFimFaixa())) {
            inss = calcularPercentual(salarioBase - faixas.get(1).getFimFaixa(), faixa);
            inss += calcularPercentual(faixas.get(1).getFimFaixa()-faixas.get(0).getFimFaixa(), faixas.get(1).getAliquota());
            inss += calcularPercentual(faixas.get(0).getFimFaixa(), faixas.get(0).getAliquota());
        }else if ((salarioBase >= faixas.get(3).getInicioFaixa()) && (salarioBase <= faixas.get(3).getFimFaixa())) {
            inss = calcularPercentual(salarioBase - faixas.get(2).getFimFaixa(), faixa);
            inss += calcularPercentual(faixas.get(2).getFimFaixa()-faixas.get(1).getFimFaixa(), faixas.get(2).getAliquota());
            inss += calcularPercentual(faixas.get(1).getFimFaixa()-faixas.get(0).getFimFaixa(), faixas.get(1).getAliquota());
            inss += calcularPercentual(faixas.get(0).getFimFaixa(), faixas.get(0).getAliquota());
        }else{
            inss = teto;
        }
    }
}

