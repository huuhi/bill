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
public class income {
    private Integer  id;
    private String date;
    private Integer  type_id;
    private Double amount;
    private String note;

    public income(String date, Integer type, Double amount, String note) {
        this.date = date;
        this.type_id = type;
        this.amount = amount;
        this.note = note;
    }
}
