package ZhiJianHu.Dao;

import ZhiJianHu.PoJO.expenseType;
import ZhiJianHu.PoJO.incomeType;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/10
 * 说明:
 */
public class incomeTypeDao extends Basic<incomeType> {
     public List<incomeType> getAll() {
          String sql = "SELECT * FROM income_type";
          return queryall(sql, incomeType.class);
     }
     public Integer getIdByName(String name){
        String sql="select id from income_type where name=?";
        return (Integer)queryOne(sql, name);
    }
    public String getNameById(Integer id){
         String sql = "SELECT name FROM income_type WHERE id=?";
         return (String)queryOne(sql, id);
    }
//    获得所有类型
    public Integer getCount(){
         String sql="select count(*) from income_type;";
         return count(sql);
    }
}