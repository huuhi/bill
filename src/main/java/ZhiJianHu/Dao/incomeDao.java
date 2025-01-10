package ZhiJianHu.Dao;

import ZhiJianHu.PoJO.expense;
import ZhiJianHu.PoJO.income;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/10
 * 说明:
 */
public class incomeDao extends Basic<income>{
     public boolean add(income income){
          String sql="insert into income(date,type_id,amount,note) values(?,?,?,?)";
          int update = update(sql, income.getDate(), income.getType_id(), income.getAmount(), income.getNote());
          return update>0;
     }
     public Integer getCount(){
          String sql="select count(*) from income";
          return count(sql);
     }
     public List<income> getList(){
          String sql="select * from income";
          return queryall(sql, income.class);
     }
}
