package iws.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import iws.beans.goods;


@Component
public class goodsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<goods> allgoods(){
		String sql="select * from goods";
		List<goods> goodslist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
	
	public List<goods> findgoods(String goodId) {
		String sql="select * from goods where goodId='"+goodId+"'";
		List<goods> goodslist=jdbcTemplate.query(sql,new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
	
	public boolean addgoods(goods goods) {
		String sql="insert into goods(goodId,category,weight,warehouseId,state) values(?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] {goods.getGoodId(),goods.getCategory(),goods.getWeight(),goods.getWarehouseId(),goods.getState()})==1;
	}
	
	public boolean deletegoods(String goodId) {
		String sql="delete from goods where goodId='"+goodId+"'";
		return jdbcTemplate.update(sql)==1;
	}
	
	//下发订单时使用，保证同一个货物不会同时出现在不同订单中
	public boolean updategoods(String goodId,String state) {
		String sql="update goods set state=? where goodId=?";
		 return jdbcTemplate.update(sql,new Object[]{state,goodId})==1;
	}
	
	//货物出库或入库完后使用，填入货物信息，并更改货物状态
	public boolean updategoods(String goodId,Double weight,String warehousId,String state) {
		String sql="update goods set weight=?,warehouseId=?,state=? where goodId=?";
		return jdbcTemplate.update(sql,new Object[]{weight,warehousId,state,goodId})==1;
	}
	
	//货物位置变更后使用，只改变货物位置、状态
	public boolean updategoods(String goodId,String warehousId,String state) {
		String sql="update goods set warehouseId=?,state=? where goodId=?";
		return jdbcTemplate.update(sql,new Object[]{warehousId,state,goodId})==1;
	}
	
	public List<goods> findbywarehouse(String warehouseid) {
		String sql="select * from goods where wareHouseId='"+warehouseid+"'";
		List<goods> goodslist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
	
	
    public List<goods> findbyorder(String orderId){
    	
    	String sql="select goods.goodId,category,weight,warehouseId,state "
    			+ "from orders join order_goods on orders.orderId=order_goods.orderId "
    			+ "where orders.orderId='"+orderId+"'";
    	List<goods> goodslist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<goods>(goods.class));
    	return goodslist;		
	}
     
}
