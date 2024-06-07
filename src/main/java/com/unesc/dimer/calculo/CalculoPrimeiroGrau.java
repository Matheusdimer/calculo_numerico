package com.unesc.dimer.calculo;

import java.util.List;
import java.util.function.Function;

public class CalculoPrimeiroGrau extends CalculoAjusteCurva {
    @Override
    public int quantidadeColunas() {
        return 2;
    }

    @Override
    public List<Function<double[], Double>> getFuncoesColunas() {
        return List.of(
                linha -> linha[X] * linha[Y],
                linha -> Math.pow(linha[X], 2)
        );
    }

    @Override
    protected double[][] getFuncaoMatrixCalc(double[] somas) {
        return new double[][] {
                // soma x ^ 2 + soma x = soma xy
                { somas[3], somas[X], somas[2] },
                // soma x + n = soma y
                { somas[X], n, somas[Y] }
        };
    }

    @Override
    public String getResultado(double[] valoresLetras) {
        return "f(x) = " + formatter.format(valoresLetras[0]) + "x + " + formatter.format(valoresLetras[1]);
    }
}
