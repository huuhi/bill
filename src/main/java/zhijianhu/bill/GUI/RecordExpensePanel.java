package zhijianhu.bill.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordExpensePanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public RecordExpensePanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setBackground(Color.WHITE);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel titleLabel = new JLabel("记录支出", SwingConstants.CENTER);
        titleLabel.setFont(new Font("楷体", Font.BOLD, 20));
        titleLabel.setForeground(Color.ORANGE);
        add(titleLabel);
        add(new JLabel()); // 空白占位符

        JLabel amountLabel = new JLabel("金额:");
        JTextField amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(200, 30));
        add(amountLabel);
        add(amountField);

        JLabel categoryLabel = new JLabel("分类:");
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.addItem("餐饮");
        categoryComboBox.addItem("交通");
        categoryComboBox.addItem("购物");
        categoryComboBox.addItem("其他");
        categoryComboBox.setPreferredSize(new Dimension(200, 30));
        add(categoryLabel);
        add(categoryComboBox);

        JLabel noteLabel = new JLabel("备注:");
        JTextArea noteArea = new JTextArea();
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(noteArea);
        scrollPane.setPreferredSize(new Dimension(200, 60));
        add(noteLabel);
        add(scrollPane);

        JLabel dateLabel = new JLabel("日期:");
        JTextField dateField = new JTextField();
        dateField.setEditable(false);
        dateField.setText(java.time.LocalDate.now().toString());
        dateField.setPreferredSize(new Dimension(200, 30));
        add(dateLabel);
        add(dateField);

        JButton backButton = createStyledButton("返回");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "expenseSummary"));
        add(backButton);

        JButton okButton = createStyledButton("确定");
        okButton.addActionListener(e -> {
            String amount = amountField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String note = noteArea.getText();
            String date = dateField.getText();

            // 这里可以添加保存记录的逻辑
            JOptionPane.showMessageDialog(this, "支出记录已保存:\n金额: " + amount + "\n分类: " + category + "\n备注: " + note + "\n日期: " + date, "成功", JOptionPane.INFORMATION_MESSAGE);

            cardLayout.show(cardPanel, "expenseSummary");
        });
        add(okButton);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("楷体", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(80, 30));
        return button;
    }
}