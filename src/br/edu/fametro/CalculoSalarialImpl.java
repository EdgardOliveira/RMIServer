package br.edu.fametro;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculoSalarialImpl extends UnicastRemoteObject implements ICalculoSalarial {

    private Salario salario;

    //Construtor padrão
    public CalculoSalarialImpl() throws RemoteException {
    }

    @Override
    public Salario calcular(Salario salario) throws RemoteException {
        //campos calculados
        INSS inss = new INSS(salario.getSalarioBase());
        double salarioBaseIRRF = salario.getSalarioBase() - inss.getINSS();
        IRRF irrf = new IRRF(salarioBaseIRRF, salario.getDependentes());
        double salarioLiquido = salarioBaseIRRF - irrf.getIRRF();

        this.salario = new Salario(
                salario.getNome(),
                salario.getDependentes(),
                salario.getSalarioBase(),
                inss.getFaixa(),
                inss.getINSS(),
                salarioBaseIRRF,
                irrf.getFaixa(),
                irrf.getIRRF(),
                salarioLiquido
        );

        exibirContraCheque();

        return this.salario;
    }

    public static void log(String mensagem) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        exibir(simpleDateFormat.format(date) + " Servidor | " + mensagem);
    }
    
    private static void exibir(String mensagem){
        System.out.println(mensagem);
    }

    private void exibirContraCheque() {
        exibir("------------------------------------------------------------------------------------------------------------");
        exibir("|\t\t\t\t\t\t\t\t\tRECIBO DE PAGAMENTO DE SALÁRIO");
        exibir("------------------------------------------------------------------------------------------------------------");
        exibir("|FUNCIONÁRIO: " + salario.getNome().toUpperCase());
        exibir("------------------------------------------------------------------------------------------------------------");
        exibir("|CÓDIGO\t| DESCRIÇÃO\t\t\t\t| REFERÊNCIA\t| VENCIMENTOS\t| DESCONTOS");
        exibir("------------------------------------------------------------------------------------------------------------");
        System.out.printf("|0001\t| SALÁRIO CONTRATUAL\t| 30D\t\t\t| R$ %5.2f\t|\n", salario.getSalarioBase());
        System.out.printf("|0520\t| INSS\t\t\t\t\t| %5.2f%%\t\t| \t\t\t\t| R$ %5.2f\n", salario.getFaixaINSS(), salario.getInss());
        System.out.printf("|0731\t| IRRF\t\t\t\t\t| %5.2f%%\t\t| \t\t\t\t| R$ %5.2f\n", salario.getFaixaIRRF(), salario.getIrrf());
        exibir("------------------------------------------------------------------------------------------------------------");
        exibir("|\t\t\t\t\t\t\t\t\t\t\t\t| TOTAL\t\t\t| TOTAL\t");
        System.out.printf("|\t\t\t\t\t\t\t\t\t\t\t\t| R$ %5.2f\t| R$ %5.2f\n", salario.getSalarioBase(), (salario.getInss() + salario.getIrrf()));
        exibir("------------------------------------------------------------------------------------------------------------");
        System.out.printf("|\t\t\t\t\t\t\t\t\t\t\t\t| LÍQUIDO ===>\t  R$ %5.2f\n", salario.getSalarioBase() - (salario.getInss() + salario.getIrrf()));
        exibir("------------------------------------------------------------------------------------------------------------");
        exibir("|SALÁRIO BASE\t| SALÁRIO INSS\t| SALÁRIO FGTS\t| FGTS\t\t| SALÁRIO IRRF\t| IRRF\t\t| FAIXA IRRF");
        exibir("------------------------------------------------------------------------------------------------------------");
        System.out.printf("|R$ %5.2f\t\t| R$ %5.2f\t| R$ %5.2f\t| R$ %5.2f\t| R$ %5.2f\t| R$ %5.2f\t| %5.2f%%\n", salario.getSalarioBase(),
                salario.getSalarioBase(), salario.getSalarioBase(), (salario.getSalarioBase() * 8/100),  salario.getSalarioBaseIRRF(), salario.getIrrf(), salario.getFaixaIRRF());
        exibir("------------------------------------------------------------------------------------------------------------");
    }
}
