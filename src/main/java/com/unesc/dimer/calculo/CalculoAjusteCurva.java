package com.unesc.dimer.calculo;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.DecompositionSolver;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public abstract class CalculoAjusteCurva {
    protected final int X = 0;
    protected final int Y = 1;
    protected final DecimalFormat formatter = new DecimalFormat("#.####", DecimalFormatSymbols.getInstance(Locale.US));

    protected double[][] matriz;
    protected int n;

    public String calcular(double[][] matrizXY) {
        n = matrizXY.length;
        montarMatriz(matrizXY);
        calcularColunas();
        double[] somas = calcularSomas();
        double[][] funcaoMatrixCalc = getFuncaoMatrixCalc(somas);
        double[] valoresLetras = calcularMatrixCalc(funcaoMatrixCalc);
        return getResultado(valoresLetras);
    }

    protected abstract int quantidadeColunas();

    private void montarMatriz(double[][] matrizXY) {
        matriz = new double[matrizXY.length + 1][quantidadeColunas() + 2];

        for (int i = 0; i < matrizXY.length; i++) {
            // Copia a matriz XY para a matriz de cálculo
            System.arraycopy(matrizXY[i], 0, matriz[i], 0, matrizXY[i].length);
        }

        System.out.println("[montagem-matriz]: " + Arrays.deepToString(matriz));
    }

    private void calcularColunas() {
        List<Function<double[], Double>> funcoesColunas = getFuncoesColunas();

        for (double[] linha : matriz) {
            for (int i = 2; i < linha.length; i++) {
                Double resultadoColuna = funcoesColunas.get(i - 2).apply(linha);
                linha[i] = resultadoColuna;
            }
        }

        System.out.println("[calculo-colunas]: " + Arrays.deepToString(matriz));
    }

    private double[] calcularSomas() {
        double[] somas = matriz[matriz.length - 1];

        for (int col = 0; col < somas.length; col++) {
            double soma = 0;
            for (int i = 0; i < matriz.length - 1; i++) {
                soma += matriz[i][col];
            }
            somas[col] = soma;
        }

        System.out.println("[somas-colunas]: " + Arrays.deepToString(matriz));
        return somas;
    }

    protected abstract List<Function<double[], Double>> getFuncoesColunas();

    protected abstract double[][] getFuncaoMatrixCalc(double[] somas);

    private double[] calcularMatrixCalc(double[][] matrixCalc) {
        double[] constants = new double[matrixCalc.length];

        // Separa a última coluna de constants (números que ficam após o =)
        for (int row = 0; row < matrixCalc.length; row++) {
            int col = matrixCalc[row].length - 1;
            constants[row] = matrixCalc[row][col];
        }

        System.out.println("[matrix-calc-constants]: " + Arrays.toString(constants));

        // Exclui a última coluna que fica após o igual
        double[][] matrizCoeficientes = new double[matrixCalc.length][matrixCalc[0].length - 1];

        for (int row = 0; row < matrixCalc.length; row++) {
            double[] linha = matrixCalc[row];

            if (linha.length - 1 >= 0) {
                System.arraycopy(linha, 0, matrizCoeficientes[row], 0, linha.length - 1);
            }
        }

        System.out.println("[matrix-calc-coefficients]: " + Arrays.deepToString(matrizCoeficientes));

        RealMatrix coefficients = new Array2DRowRealMatrix(matrizCoeficientes, true);
        DecompositionSolver solver = new LUDecompositionImpl(coefficients).getSolver();

        double[] resultado = solver.solve(constants);

        System.out.println("[matrix-calc-resultado]: " + Arrays.toString(resultado));

        return resultado; // [a = ?, b = ? ...]
    }

    protected abstract String getResultado(double[] valoresLetras);
}
