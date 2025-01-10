package ZhiJianHu.GUI;

import ZhiJianHu.Dao.expenseDao;
import ZhiJianHu.Dao.expenseTypeDao;
import ZhiJianHu.PoJO.expense;
import ZhiJianHu.PoJO.expenseType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Vector;



public class ExpenseSummaryPanel extends JPanel {

    private DefaultTableModel model;
    private JComboBox<String> timeComboBox;
    private JComboBox<String> categoryComboBox;
    private JTable table;
    private JLabel totalLabel;
    private Vector<Vector<Object>> originalData;
    private expenseDao ed;
    private expenseTypeDao etd;
    private Object[][] data;


    public ExpenseSummaryPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // 标题
        JLabel titleLabel = new JLabel("统计支出", SwingConstants.CENTER);
        titleLabel.setFont(new Font("楷体", Font.BOLD, 20));
        titleLabel.setForeground(Color.RED);
        add(titleLabel, BorderLayout.NORTH);

        ed=new expenseDao();
        etd=new expenseTypeDao();
        int co = ed.co();
        // 表格数据
        String[] columnNames = {"日期", "分类", "金额", "备注"};
        data=new Object[co][4];
        addData();

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        table.setFont(new Font("楷体", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 保存原始数据
        originalData = new Vector<>();
        for (Object[] row : data) {
            Vector<Object> rowData = new Vector<>();
            for (Object cell : row) {
                rowData.add(cell);
            }
            originalData.add(rowData);
        }

        // 统计部分
        totalLabel = new JLabel("总支出: " + calculateTotal() + " 元", SwingConstants.CENTER);
        totalLabel.setFont(new Font("楷体", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLACK);
        add(totalLabel, BorderLayout.SOUTH);

        // 查询面板
        JPanel queryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // 时间下拉选项
        JLabel timeLabel = new JLabel("时间:");
        timeComboBox = new JComboBox<>(new String[]{"全部", "最近一年", "最近一月", "最近一周", "最近一天"});
        queryPanel.add(timeLabel);
        queryPanel.add(timeComboBox);

        // 分类下拉选项
        JLabel categoryLabel = new JLabel("分类:");
        addSearchType();
        queryPanel.add(categoryLabel);
        queryPanel.add(categoryComboBox);

        // 查询按钮
        JButton queryButton = new JButton("查询");
        queryButton.addActionListener(e -> performQuery());
        queryPanel.add(queryButton);

        add(queryPanel, BorderLayout.NORTH);
    }

    public void addSearchType() {
        List<expenseType> types = etd.allType();
        Integer count = etd.getCount();
        String[] ty=new String[count];
        int index=0;
        for (expenseType type:types){
            ty[index++]=type.getName();
        }
        categoryComboBox=new JComboBox<>(ty);
    }


     public void addData() {
        List<expense> all = ed.getAll();
        int index=0;
        for(expense e:all){
            data[index][0]=e.getDate();
            String type = etd.getNameById(e.getType_id());
            data[index][1]=type;
            data[index][2]=e.getAmount();
            data[index][3]=e.getNote();
            index++;
        }
    }

    private void performQuery() {
        String selectedTime = (String) timeComboBox.getSelectedItem();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();

        Vector<Vector<Object>> filteredData = new Vector<>();

        if (selectedTime.equals("全部") && selectedCategory.equals("全部")) {
            // 如果时间和分类都选择“全部”，则恢复所有数据
            filteredData.addAll(originalData);
        } else {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (Vector<Object> row : originalData) {
                boolean match = true;

                if (!selectedTime.equals("全部")) {
                    try {
                        LocalDate date = LocalDate.parse((String) row.get(0), formatter);

                        switch (selectedTime) {
                            case "最近一年":
                                if (ChronoUnit.DAYS.between(date, currentDate) > 365) {
                                    match = false;
                                }
                                break;
                            case "最近一月":
                                if (ChronoUnit.DAYS.between(date, currentDate) > 30) {
                                    match = false;
                                }
                                break;
                            case "最近一周":
                                if (ChronoUnit.DAYS.between(date, currentDate) > 7) {
                                    match = false;
                                }
                                break;
                            case "最近一天":
                                if (ChronoUnit.DAYS.between(date, currentDate) > 1) {
                                    match = false;
                                }
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        match = false;
                    }
                }

                if (!selectedCategory.equals("全部")) {
                    String category = (String) row.get(1);
                    if (!category.equals(selectedCategory)) {
                        match = false;
                    }
                }

                if (match) {
                    filteredData.add(row);
                }
            }
        }

        // 获取列标识符
        Vector<String> columnIdentifiers = new Vector<>();
        for (int i = 0; i < model.getColumnCount(); i++) {
            columnIdentifiers.add(model.getColumnName(i));
        }

        model.setDataVector(filteredData, columnIdentifiers);
        table.updateUI();

        // 更新统计信息
        totalLabel.setText("总支出: " + calculateTotal(filteredData) + " 元");
    }

    private double calculateTotal(Vector<Vector<Object>> data) {
        double total = 0.0;
        if(data==null){
            return total;
        }
        for (Vector<Object> row : data) {
            try {
                total += Double.parseDouble(row.get(2).toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    private double calculateTotal() {
        return calculateTotal(originalData);
    }

}
