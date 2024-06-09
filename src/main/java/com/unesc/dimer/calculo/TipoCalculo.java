package com.unesc.dimer.calculo;

public enum TipoCalculo {
    PRIMEIRO_GRAU("Primeiro grau", new CalculoPrimeiroGrau()),
    SEGUNDO_GRAU("Segundo grau", new CalculoSegundoGrau()),
    TERCEIRO_GRAU("Terceiro grau", new CalculoTerceiroGrau());

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
