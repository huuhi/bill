import ZhiJianHu.PoJO.expenseType;
import ZhiJianHu.Utils.DruidPoor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/10
 * 说明:
 */
public class TestCOn {
    @Test
    public void test() throws SQLException {
        DruidPoor d = new DruidPoor();
        String sql="SELECT * FROM expense_type";
        Connection conn = d.con();
        QueryRunner qr = new QueryRunner();
        qr.query(conn,sql,new BeanListHandler<>(expenseType.class)).forEach(System.out::println);

    }
}
