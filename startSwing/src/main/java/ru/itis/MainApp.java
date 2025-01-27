package ru.itis;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {

    private JTextField tfName, tfEmail, tfAge;
    private JButton btnSave, btnCancel, btnHelp;

    public MainApp() {
        super("Форма ввода данных");

        // Настройка основного окна
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLayout(new GridLayout(5, 2, 10, 10)); // Сетка для размещения компонентов

        // Метки и поля ввода
        JLabel lblName = new JLabel("Имя:");
        JLabel lblEmail = new JLabel("Электронная почта:");
        JLabel lblAge = new JLabel("Возраст:");
        tfName = new JTextField();
        tfEmail = new JTextField();
        tfAge = new JTextField();

        // Кнопки
        btnSave = new JButton("Сохранить");
        btnCancel = new JButton("Отменить");
        btnHelp = new JButton("Справка");

        // Добавление компонентов в окно
        this.add(lblName);
        this.add(tfName);
        this.add(lblEmail);
        this.add(tfEmail);
        this.add(lblAge);
        this.add(tfAge);
        this.add(btnSave);
        this.add(btnCancel);
        this.add(new JLabel()); // Пустое пространство
        this.add(btnHelp);

        // Обработчики событий
        btnCancel.addActionListener(e -> clearFields());
        btnHelp.addActionListener(e -> showHelpDialog());
        btnSave.addActionListener(e -> saveData());

        // Показ окна
        this.setVisible(true);
    }

    // Очистка всех полей ввода
    private void clearFields() {
        tfName.setText("");
        tfEmail.setText("");
        tfAge.setText("");
    }

    // Диалог справки
    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this,
                "Эта форма позволяет ввести имя и электронную почту.\n" +
                "Кнопка \"Сохранить\" сохраняет данные в базу данных.\n" +
                "Кнопка \"Отменить\" очищает все поля.",
                "Справка",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Сохранение данных через сервисный слой
    private void saveData() {
        String name = tfName.getText();
        String email = tfEmail.getText();
        int age;

        try {
            age = Integer.parseInt(tfAge.getText());
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Пожалуйста введите число в графе возраста",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (name.isEmpty() || email.isEmpty() || tfAge.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Пожалуйста, заполните все поля!",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserService userService = new UserService(new UserRepository());
        boolean isSaved = userService.saveUser(new User(name, email, age));

        if (isSaved) {
            JOptionPane.showMessageDialog(this,
                    "Данные успешно сохранены!",
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Ошибка сохранения данных!",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }

}