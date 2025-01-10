package ZhiJianHu.GUI;

import ZhiJianHu.Dao.incomeDao;
import ZhiJianHu.Dao.incomeTypeDao;
import ZhiJianHu.PoJO.income;
import ZhiJianHu.PoJO.incomeType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Vector;

public class IncomeSummaryPanel extends JPanel {

    private DefaultTableModel model;
    private JComboBox<String> timeComboBox;
    private JComboBox<String> categoryComboBox;
    private JTable table;
    private Vector<Vector<Object>> originalData;
    private JLabel totalLabel;

    private incomeDao icDao;
    private incomeTypeDao itDao;

    public IncomeSummaryPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // 标题
        JLabel titleLabel = new JLabel("统计收入", SwingConstants.CENTER);
        titleLabel.setFont(new Font("楷体", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.NORTH);

        // 查询面板
        JPanel queryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // 时间下拉选项
        JLabel timeLabel = new JLabel("时间:");
        timeComboBox = new JComboBox<>(new String[]{"全部", "最近一年", "最近一月", "最近一周", "最近一天"});
        queryPanel.add(timeLabel);
        queryPanel.add(timeComboBox);


        icDao=new incomeDao();
        itDao=new incomeTypeDao();

        // 分类下拉选项
        JLabel categoryLabel = new JLabel("分类:");
        addSearchType();
        queryPanel.add(categoryLabel);
        queryPanel.add(categoryComboBox);

        // 查询按钮
        JButton queryButton = new JButton("查询");
        queryButton.addActionListener(e -> performQuery());
        queryPanel.add(queryButton);

        add(queryPanel, BorderLayout.NORTH); // 将查询面板放在表格上方

        int count = icDao.getCount();
        // 表格数据
        String[] columnNames = {"日期", "分类", "金额", "备注"};
        Object[][] data = new Object[count][4];
        addData(data);

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
        totalLabel = new JLabel("总收入: " + calculateTotal(originalData) + " 元", SwingConstants.CENTER);
        totalLabel.setFont(new Font("楷体", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLACK);
        add(totalLabel, BorderLayout.SOUTH); // 将统计部分放在表格下方
    }

    private void addSearchType() {
        Integer count = itDao.getCount();
        String[] ty=new String[count];
        List<incomeType> all = itDao.getAll();
        int index=0;
        for(incomeType type:all) {
            ty[index++] = type.getName();
        }
        categoryComboBox=new JComboBox<>(ty);
    }

    private void addData(Object[][] data) {
        List<income> list = icDao.getList();
        int index=0;
        for (income income : list) {
            String date = income.getDate().toString();
            String category = itDao.getNameById(income.getType_id());
            double amount = income.getAmount();
            String remark = income.getNote();
            data[index][0] = date;
            data[index][1] = category;
            data[index][2] = amount;
            data[index][3] = remark;
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
                                if (ChronoUnit.YEARS.between(date, currentDate) >= 1) {
                                    match = false;
                                }
                                break;
                            case "最近一月":
                                if (ChronoUnit.MONTHS.between(date, currentDate) >= 1) {
                                    match = false;
                                }
                                break;
                            case "最近一周":
                                if (ChronoUnit.WEEKS.between(date, currentDate) >= 1) {
                                    match = false;
                                }
                                break;
                            case "最近一天":
                                if (ChronoUnit.DAYS.between(date, currentDate) >= 1) {
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
        totalLabel.setText("总收入: " + calculateTotal(filteredData) + " 元");
    }

    private double calculateTotal(Vector<Vector<Object>> data) {
        double total = 0.0;
        if(data==null){
            return total;
        }
        for (Vector<Object> row : data) {
            try {
                Object o = row.get(2);
                if(o==null){
                    return total;
                }
                total += Double.parseDouble(o.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return total;
    }
}



