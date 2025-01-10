package ZhiJianHu.PoJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/10
 * 说明:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class expense {
    private Integer id;
    private String date;
    private Integer type_id;
    private double amount;
    private String note;

    public expense(String date, Integer typeId, double amount, String note) {
        this.date = date;
        this.type_id = typeId;
        this.amount = amount;
        this.note = note;
    }
}
