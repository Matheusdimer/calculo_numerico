package com.unesc.dimer.view;

import com.unesc.dimer.calculo.TipoCalculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
    private JPanel panel;
    private JTable table;
    private JComboBox<TipoCalculo> combo;
    private JButton calcularButton;
    private JButton excluirLinhaButton;
    private JButton addLinhaButton;
    private JTextField resultadoField;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"X", "Y"}, 1);

    public MainWindow() {
        super("Ajuste de curva");
        table.setModel(model);
        //matrizTestes();

        combo.setModel(new DefaultComboBoxModel<>(TipoCalculo.values()));
        configureClickButtons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setSize(400, 300);
        setVisible(true);
    }

    private void matrizTestes() {
        model.setRowCount(0);
        model.addRow(new Object[]{"-3", "-7.4"});
        model.addRow(new Object[]{"-2", "-5.8"});
        model.addRow(new Object[]{"-1", "-4.6"});
        model.addRow(new Object[]{"1", "-1.3"});
        model.addRow(new Object[]{"4", "2.8"});
    }

    private void configureClickButtons() {
        addLinhaButton.addActionListener(e -> model.setRowCount(model.getRowCount() + 1));
        excluirLinhaButton.addActionListener(e -> {
            if (model.getRowCount() > 1) {
                model.setRowCount(model.getRowCount() - 1);
            }
        });

        calcularButton.addActionListener(this::calcular);
    }

    private void calcular(ActionEvent e) {
        double[][] matriz = new double[model.getRowCount()][model.getColumnCount()];

        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                matriz[i][j] = Double.parseDouble(model.getValueAt(i, j).toString());
            }
        }

        TipoCalculo tipoCalculo = (TipoCalculo) combo.getSelectedItem();
        String resultado = tipoCalculo.getCalculoAjusteCurva().calcular(matriz);

        resultadoField.setText(resultado);
    }
}
