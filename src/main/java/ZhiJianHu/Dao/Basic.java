package ZhiJianHu.Dao;

import ZhiJianHu.Utils.DruidPoor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/10
 * 说明:
 */
public class Basic<T>{
    QueryRunner qr=new QueryRunner();

    public List<T> queryall(String sql,Class<T> cl){
        Connection con1 = DruidPoor.con();
        try {
            return qr.query(con1,sql,new BeanListHandler<>(cl));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidPoor.close(con1);
        }
    }
    public int update(String sql,Object... params){
        Connection con1 = DruidPoor.con();
        try {
            return qr.update(con1,sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidPoor.close(con1);
        }
    }
    public Object queryOne(String sql, Object... params){
        Connection con1 = DruidPoor.con();
        try {
            return qr.query(con1,sql,new ScalarHandler<>(),params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidPoor.close(con1);
        }
    }
    public Integer count(String sql){
        Connection con1 = DruidPoor.con();
        try {
            Long result = qr.query(con1, sql, new ScalarHandler<>());
            if (result == null) {
                return null;
            }
            // 检查是否超出 Integer 范围
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                throw new ArithmeticException("Result out of Integer range");
            }
            return result.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidPoor.close(con1);
        }
    }

}
