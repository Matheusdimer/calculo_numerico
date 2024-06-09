package com.unesc.dimer.calculo;

import java.util.List;
import java.util.function.Function;

public class CalculoSegundoGrau extends CalculoAjusteCurva{
    @Override
    protected int quantidadeColunas() {
        return 5;
    }

    @Override
    protected List<Function<double[], Double>> getFuncoesColunas() {
        return List.of(
                linha -> linha[X] * linha[Y],
                linha -> Math.pow(linha[X], 2),
                linha -> Math.pow(linha[X], 2) * linha[Y],
                linha -> Math.pow(linha[X], 3),
                linha -> Math.pow(linha[X], 4)
        );
    }

    @Override
    protected double[][] getFuncaoMatrixCalc(double[] somas) {
        return new double[][] {
                // soma x^2 + soma x + n = soma y
                { somas[3], somas[X], n, somas[Y] },
                // soma x^3 + soma x^2 + soma x = soma x*y
                { somas[5], somas[3], somas[X], somas[2] },
                // soma x^4 + soma x^3 + soma x^2 = soma x^2*y
                { somas[6], somas[5], somas[3], somas[4] }
        };
    }

    @Override
    protected String getResultado(double[] valoresLetras) {
        return "f(x) = " + formatter.format(valoresLetras[0]) + "x^2 + " + formatter.format(valoresLetras[1]) + "x + " + formatter.format(valoresLetras[2]);
    }
}
