package ZhiJianHu.GUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class NavPanel extends JPanel {

    public NavPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new FlowLayout());

        JButton incomeSummaryButton = createButton("img/home.png", "统计收入", "incomeSummary", cardLayout, cardPanel);
        JButton expenseSummaryButton = createButton("img/report.png", "统计支出", "expenseSummary", cardLayout, cardPanel);
        JButton recordIncomeButton = createButton("img/record.png", "记录收入", "recordIncome", cardLayout, cardPanel);
        JButton recordExpenseButton = createButton("img/category1.png", "记录支出", "recordExpense", cardLayout, cardPanel);

        add(incomeSummaryButton);
        add(expenseSummaryButton);
        add(recordIncomeButton);
        add(recordExpenseButton);
    }

    private JButton createButton(String iconPath, String text, String cardName, CardLayout cardLayout, JPanel cardPanel) {
    // 使用相对路径，并去掉多余的反斜杠
        URL location = getClass().getResource("/" + iconPath);

        if (location == null) {
            System.err.println("Resource not found: " + iconPath);
            JButton button = new JButton(text);
            button.setFont(new Font("楷体", Font.BOLD, 14));
            button.addActionListener(e -> cardLayout.show(cardPanel, cardName));
            return button;
        }

        ImageIcon originalIcon = new ImageIcon(location);
    Image img = originalIcon.getImage();
    // 设置新的宽度和高度（例如50x50）
    Image scaledImg = img.getScaledInstance(16, 24, Image.SCALE_SMOOTH);
    ImageIcon icon = new ImageIcon(scaledImg);

    JButton button = new JButton(icon);
    button.setHorizontalTextPosition(JButton.CENTER);
    button.setVerticalTextPosition(JButton.BOTTOM);
    button.setText(text);
    button.setFont(new Font("楷体", Font.BOLD, 14));
    button.addActionListener(e -> cardLayout.show(cardPanel, cardName));
    return button;
}

}



