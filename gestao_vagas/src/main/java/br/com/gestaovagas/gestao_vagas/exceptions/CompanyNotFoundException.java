package br.com.gestaovagas.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Companhia não encontrada.");
    }
}
