/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemanotasapp;

/**
 *
 * @author axelm
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SistemaNotas extends JFrame {

    private JTable tablaEstudiantes, tablaMaterias, tablaGrupos, tablaNotas;
    private DefaultTableModel modeloEstudiantes, modeloMaterias, modeloGrupos, modeloNotas;
    private JComboBox<String> comboEstudiantes, comboGrupos;
    private JTextField campoEstudiante, campoMateria, campoGrupo, campoNota;

    public SistemaNotas() {
        setTitle("Sistema de Notas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();

        pestañas.add("Estudiantes", crearPanelEstudiantes());
        pestañas.add("Materias", crearPanelMaterias());
        pestañas.add("Grupos", crearPanelGrupos());
        pestañas.add("Control de Notas", crearPanelNotas());

        add(pestañas);
    }

    private JPanel crearPanelEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout());
        modeloEstudiantes = new DefaultTableModel(new String[]{"Nombre"}, 0);
        tablaEstudiantes = new JTable(modeloEstudiantes);

        JPanel input = new JPanel(new FlowLayout());
        campoEstudiante = new JTextField(20);
        JButton btnAgregar = new JButton("Agregar Estudiante");
        input.add(new JLabel("Nombre:"));
        input.add(campoEstudiante);
        input.add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            String nombre = campoEstudiante.getText().trim();
            if (!nombre.isEmpty()) {
                modeloEstudiantes.addRow(new Object[]{nombre});
                campoEstudiante.setText("");
                actualizarComboBox(modeloEstudiantes, comboEstudiantes);
            } else {
                mostrarError("Ingrese un nombre válido.");
            }
        });

        panel.add(input, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelMaterias() {
        JPanel panel = new JPanel(new BorderLayout());
        modeloMaterias = new DefaultTableModel(new String[]{"Materia"}, 0);
        tablaMaterias = new JTable(modeloMaterias);

        JPanel input = new JPanel(new FlowLayout());
        campoMateria = new JTextField(20);
        JButton btnAgregar = new JButton("Agregar Materia");
        input.add(new JLabel("Materia:"));
        input.add(campoMateria);
        input.add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            String materia = campoMateria.getText().trim();
            if (!materia.isEmpty()) {
                modeloMaterias.addRow(new Object[]{materia});
                campoMateria.setText("");
            } else {
                mostrarError("Ingrese una materia válida.");
            }
        });

        panel.add(input, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaMaterias), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelGrupos() {
        JPanel panel = new JPanel(new BorderLayout());
        modeloGrupos = new DefaultTableModel(new String[]{"Grupo"}, 0);
        tablaGrupos = new JTable(modeloGrupos);

        JPanel input = new JPanel(new FlowLayout());
        campoGrupo = new JTextField(20);
        JButton btnAgregar = new JButton("Agregar Grupo");
        input.add(new JLabel("Grupo:"));
        input.add(campoGrupo);
        input.add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            String grupo = campoGrupo.getText().trim();
            if (!grupo.isEmpty()) {
                modeloGrupos.addRow(new Object[]{grupo});
                campoGrupo.setText("");
                actualizarComboBox(modeloGrupos, comboGrupos);
            } else {
                mostrarError("Ingrese un grupo válido.");
            }
        });

        panel.add(input, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaGrupos), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelNotas() {
        JPanel panel = new JPanel(new BorderLayout());
        modeloNotas = new DefaultTableModel(new String[]{"Estudiante", "Grupo", "Nota"}, 0);
        tablaNotas = new JTable(modeloNotas);

        JPanel input = new JPanel(new GridLayout(2, 4, 10, 10));
        comboEstudiantes = new JComboBox<>();
        comboGrupos = new JComboBox<>();
        campoNota = new JTextField();
        JButton btnAgregarNota = new JButton("Agregar Nota");

        input.add(new JLabel("Estudiante:"));
        input.add(comboEstudiantes);
        input.add(new JLabel("Grupo:"));
        input.add(comboGrupos);
        input.add(new JLabel("Nota (0-100):"));
        input.add(campoNota);
        input.add(new JLabel(""));
        input.add(btnAgregarNota);

        btnAgregarNota.addActionListener(e -> {
            String estudiante = (String) comboEstudiantes.getSelectedItem();
            String grupo = (String) comboGrupos.getSelectedItem();
            String notaStr = campoNota.getText().trim();

            if (estudiante != null && grupo != null && !notaStr.isEmpty()) {
                try {
                    double nota = Double.parseDouble(notaStr);
                    if (nota >= 0 && nota <= 100) {
                        modeloNotas.addRow(new Object[]{estudiante, grupo, nota});
                        campoNota.setText("");
                    } else {
                        mostrarError("La nota debe estar entre 0 y 100.");
                    }
                } catch (NumberFormatException ex) {
                    mostrarError("Ingrese una nota numérica válida.");
                }
            } else {
                mostrarError("Complete todos los campos.");
            }
        });

        panel.add(input, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaNotas), BorderLayout.CENTER);
        return panel;
    }

    private void actualizarComboBox(DefaultTableModel modelo, JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            comboBox.addItem(modelo.getValueAt(i, 0).toString());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaNotas().setVisible(true));
    }
}

