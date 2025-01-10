package ZhiJianHu.Dao;

import ZhiJianHu.PoJO.expense;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/10
 * 说明:
 */
public class expenseDao extends Basic<expense>{
     public boolean add(expense expense){
          String sql="insert into expense(date,type_id,amount,note) values(?,?,?,?)";
          int update = update(sql, expense.getDate(), expense.getType_id(), expense.getAmount(), expense.getNote());
          return update>0;
     }
     public Integer co(){
          String sql="select count(*) from expense";
          return count(sql);
     }
//     获得所有数据
     public List<expense> getAll(){
          String sql="select * from expense";
          return queryall(sql, expense.class);
     }
}
