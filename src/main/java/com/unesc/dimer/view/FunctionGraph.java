package com.unesc.dimer.view;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class FunctionGraph extends JFrame {
    public FunctionGraph(String function, double[][] xy) {
        super("Gráfico de função");

        // Cria a série de dados
        XYSeries series = new XYSeries(function);

        // Cria a expressão a partir da string da função
        Expression expression = new ExpressionBuilder(function.replace("f(x) = ", ""))
                .variables("x")
                .build();

        double[] minMax = getMinMax(xy);

        // Adiciona os pontos da função à série de dados
        for (double x = minMax[0] - 10; x <= minMax[1] + 10; x += 0.1) {
            expression.setVariable("x", x);
            double y = expression.evaluate();
            series.add(x, y);
        }

        // Cria a série de dados para os pontos soltos
        XYSeries pointsSeries = new XYSeries("Valores da tabela");
        for (double[] point : xy) {
            pointsSeries.add(point[0], point[1]);
        }

        // Cria um dataset com a série de dados
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        dataset.addSeries(pointsSeries);

        // Cria o gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfico de Função",
                "Eixo X",
                "Eixo Y",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Personaliza os pontos soltos
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = getXyLineAndShapeRenderer();

        plot.setRenderer(renderer);

        // Adiciona o gráfico a um painel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private XYLineAndShapeRenderer getXyLineAndShapeRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // Index 0 para a série da função (apenas linhas)
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);

        // Index 1 para os pontos soltos (apenas formas)
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        Shape shape = new Ellipse2D.Double(-3, -3, 6, 6); // Forma de um círculo
        renderer.setSeriesShape(1, shape);
        return renderer;
    }

    private double[] getMinMax(double[][] pontos) {
        // Inicializa com o primeiro ponto
        double menorX = pontos[0][0];
        double maiorX = pontos[0][0];

        // Itera sobre o array para encontrar o menor e maior X
        for (int i = 1; i < pontos.length; i++) {
            if (pontos[i][0] < menorX) {
                menorX = pontos[i][0];
            }
            if (pontos[i][0] > maiorX) {
                maiorX = pontos[i][0];
            }
        }

        return new double[] { menorX, maiorX };
    }
}
