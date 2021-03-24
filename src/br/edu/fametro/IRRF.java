package br.edu.fametro;

import java.util.ArrayList;
import java.util.List;

import static br.edu.fametro.Rotinas.calcularPercentual;

public class IRRF {
    //declaração de constantes
    private double DEPENDENTE_IRRF = 189.59;

    //declaração de variáveis
    private double deducaoIRRF;
    private double faixa;
    private double irrf;
    private List<Aliquota> faixas = new ArrayList<>();

    public double getDependenteIRRF() {
        return DEPENDENTE_IRRF;
    }

    public double getDeducaoIRRF() {
        return deducaoIRRF;
    }

    public double getFaixa() {
        return faixa;
    }

    public double getIRRF() {
        return irrf;
    }

    //construtor vazio
    public IRRF() {
        configurarTabela();
    }

    //construtor com parâmetros
    public IRRF(double salarioBase, int dependentes) {
        configurarTabela();
        calcularFaixa(salarioBase);
        calcularIRRF(salarioBase, faixa, dependentes);
    }

    private void configurarTabela() {
        faixas.add(new Aliquota(0.0, 1903.98, 0.0, 0.0));
        faixas.add(new Aliquota(1903.99, 2826.65, 7.5, 142.80));
        faixas.add(new Aliquota(2826.66, 3751.05, 15.0, 354.80));
        faixas.add(new Aliquota(3751.06, 4664.68, 22.5, 636.13));
        faixas.add(new Aliquota(4664.69,4664.69, 27.5, 869.36));
    }

    public void calcularFaixa(double salarioBase) {
        for (Aliquota aliquota : faixas) {
            if ((salarioBase >= aliquota.getInicioFaixa()) && (salarioBase <= aliquota.getFimFaixa())) {
                faixa = aliquota.getAliquota();
                deducaoIRRF = aliquota.getDeducao();
            }else if ((salarioBase >= aliquota.getInicioFaixa()) && (salarioBase >= aliquota.getFimFaixa())) {
                faixa = aliquota.getAliquota();
                deducaoIRRF = aliquota.getDeducao();
            }
        }
    }

    public void calcularIRRF(double salarioBase, double faixa, int dependentes) {
        irrf = calcularPercentual(salarioBase, faixa);
        double descontos = deducaoIRRF + (dependentes * DEPENDENTE_IRRF);
        irrf = irrf >= descontos ? irrf -= descontos : 0.0;
    }
}
