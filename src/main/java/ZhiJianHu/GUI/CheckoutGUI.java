package ZhiJianHu.GUI;

import javax.swing.*;
import java.awt.*;

public class CheckoutGUI extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public CheckoutGUI() {
        setTitle("结账GUI");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建卡片布局面板
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // 添加各个页面
        cardPanel.add(new IncomeSummaryPanel(), "incomeSummary");
        cardPanel.add(new ExpenseSummaryPanel(), "expenseSummary");
        cardPanel.add(new RecordIncomePanel(cardLayout, cardPanel), "recordIncome");
        cardPanel.add(new RecordExpensePanel(cardLayout, cardPanel), "recordExpense");

        // 创建导航栏
        NavPanel navPanel = new NavPanel(cardLayout, cardPanel);

        // 设置主布局
        setLayout(new BorderLayout());
        add(navPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        // 默认显示统计收入页面
        cardLayout.show(cardPanel, "recordExpense");
        setLocationRelativeTo(null);
    }

}