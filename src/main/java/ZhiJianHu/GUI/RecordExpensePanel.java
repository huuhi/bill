package ZhiJianHu.GUI;

import ZhiJianHu.Dao.expenseDao;
import ZhiJianHu.Dao.expenseTypeDao;
import ZhiJianHu.PoJO.expense;
import ZhiJianHu.PoJO.expenseType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RecordExpensePanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private expenseDao ed;
    private expenseTypeDao etd;

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


        ed=new expenseDao();
        etd=new expenseTypeDao();

        JLabel categoryLabel = new JLabel("分类:");
        JComboBox<String> categoryComboBox = new JComboBox<>();
        addItems(categoryComboBox);
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
            double amount = Double.parseDouble( amountField.getText());
//分类
            String category = (String) categoryComboBox.getSelectedItem();
            String note = noteArea.getText();
            String date = dateField.getText();

            Integer typeId =etd.getIdByName(category);
            ed.add(new expense(date, typeId,amount,note));
            // 这里可以添加保存记录的逻辑
//           这里应该刷新

            JOptionPane.showMessageDialog(this, "支出记录已保存:\n金额: " + amount + "\n分类: " + category + "\n备注: " + note + "\n日期: " + date, "成功", JOptionPane.INFORMATION_MESSAGE);

            cardLayout.show(cardPanel, "expenseSummary");
        });
        add(okButton);
    }

    private void addItems(JComboBox<String> Box) {
//        expenseTypeDao etd = new expenseTypeDao();
        List<expenseType> et =etd.allType();
        for (expenseType e : et) {
            Box.addItem(e.getName());
        }

    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("楷体", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(80, 30));
        return button;
    }
}