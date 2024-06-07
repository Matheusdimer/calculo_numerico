package com.unesc.dimer.calculo;

public enum TipoCalculo {
    PRIMEIRO_GRAU("Primeiro grau", new CalculoPrimeiroGrau());

    private final String descricao;
    private final CalculoAjusteCurva calculoAjusteCurva;


    TipoCalculo(String descricao, CalculoAjusteCurva calculoAjusteCurva) {
        this.descricao = descricao;
        this.calculoAjusteCurva = calculoAjusteCurva;
    }

    public CalculoAjusteCurva getCalculoAjusteCurva() {
        return calculoAjusteCurva;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
