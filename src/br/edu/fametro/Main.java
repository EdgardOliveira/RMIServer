package br.edu.fametro;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static br.edu.fametro.CalculoSalarialImpl.log;

public class Main {

    private static final int PORTA = 1099;

    public static void main(String[] args) {
        try{
            ICalculoSalarial iCalculoSalarial = new CalculoSalarialImpl();
            Registry registry = LocateRegistry.createRegistry(PORTA);
            registry.rebind("Servidor", iCalculoSalarial);
            log("Registrado e pronto para aceitar requisições...");
        }catch (Exception e){
            log("Ocorreu um erro inesperado.\nErro: " + e.getMessage());
        }
    }
}
