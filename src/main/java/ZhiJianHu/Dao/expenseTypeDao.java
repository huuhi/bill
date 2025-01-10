package ZhiJianHu.Dao;

import ZhiJianHu.PoJO.expenseType;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/1/10
 * 说明:
 */
public class expenseTypeDao extends Basic<expenseType>{
    public List<expenseType> allType(){
        String sql="select * from expense_type";
        return queryall(sql,expenseType.class);
    }
    //根据类型名称查询id
    public Integer getIdByName(String name){
        String sql="select id from expense_type where name=?";
        return (Integer)queryOne(sql,name);
    }
    //根据id查询类型名称
    public String getNameById(int id){
        String sql="select name from expense_type where id=?";
        return (String)queryOne(sql,id);
    }
    public Integer getCount(){
        String sql="select count(*) from expense_type;";
        return count(sql);
    }
}
