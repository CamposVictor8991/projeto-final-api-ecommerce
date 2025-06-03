package org.serratec.TrabalhoFinalAPI.util;

public class DescontoQuantidadeUtil {

    public static double calcularDesconto(int quantidadeTotalItens) {
        if (quantidadeTotalItens > 50) {
            return 0.15; // 15% de desconto
        } else if (quantidadeTotalItens > 30) {
            return 0.10; // 10% de desconto
        } else if (quantidadeTotalItens > 10) {
            return 0.05; // 5% de desconto
        }
        return 0.0; // Sem desconto
    }

}
