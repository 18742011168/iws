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
	
	//用于入库
	public List<goods> findsimilargoods1(String category,String state,String inPlaceTime){
		String sql="select * from goods "
				+ "where category='"+category+"' and warehouseId is NULL and inPlaceTime<='"+inPlaceTime+"' and state='"+state+"'"
						+ " ORDER BY inPlaceTime ASC";
		List<goods> goodslist=jdbcTemplate.query(sql,new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
	
	//用于出库或者位置变更
	public List<goods> findsimilargoods2(String category,String state,String warehouseId,String inPlaceTime){
		String sql="select * from goods "
				+ "where category='"+category+"' and warehouseId='"+warehouseId+"' and inPlaceTime<='"+inPlaceTime+"' and state='"+state+"'"
						+ "ORDER BY inPlaceTime ASC";
						
		List<goods> goodslist=jdbcTemplate.query(sql,new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
	public boolean addgoods(goods goods) {
		String sql="insert into goods(goodId,category,weight,warehouseId,state,inPlaceTime) values(?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] {goods.getGoodId(),goods.getCategory(),goods.getWeight(),goods.getWarehouseId(),goods.getState(),goods.getInPlaceTime()})==1;
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
	public boolean updategoods(String goodId,Double weight,String warehousId,String state,String inPlaceTime) {
		String sql="update goods set weight=?,warehouseId=?,state=?,inPlaceTime=? where goodId=?";
		return jdbcTemplate.update(sql,new Object[]{weight,warehousId,state,inPlaceTime,goodId})==1;
	}
	
	//货物位置变更后使用，只改变货物位置、状态
	public boolean updategoods(String goodId,String warehousId,String state) {
		String sql="update goods set warehouseId=?,state=? where goodId=?";
		return jdbcTemplate.update(sql,new Object[]{warehousId,state,goodId})==1;
	}
	
	//manager更新货物使用
	public boolean updategoods(String goodId,String category,Double weight,String state) {
		String sql="update goods set category=?,weight=?,state=? where goodId=?";
		return jdbcTemplate.update(sql,new Object[]{category,weight,state,goodId})==1;
	}
	
	public List<goods> findbywarehouse(String warehouseid) {
		String sql="select * from goods where wareHouseId='"+warehouseid+"'";
		List<goods> goodslist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
    
	public int warehousegoodsnumber(String warehouseid) {
		String sql="select count(*) from goods where wareHouseId='"+warehouseid+"'";
		int result=jdbcTemplate.queryForObject(sql,Integer.class);
		return result;
	}
	
    public List<goods> findbyorder(String orderId){
    	
    	String sql="select goods.goodId,category,weight,warehouseId,state "
    			+ "from goods join order_goods on order_goods.goodId=goods.goodId "
    			+ "where orderId='"+orderId+"'";
    	List<goods> goodslist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<goods>(goods.class));
    	return goodslist;		
	}
    
    public int ordergoodsnumber(String orderId) {
    	String sql="select count(*) from goods where where orderId='"+orderId+"'";
    	int result=jdbcTemplate.queryForObject(sql,Integer.class);
		return result;
    }
    public int goodsnumber() {
		 String sql="select count(*) from goods";
		 int result=jdbcTemplate.queryForObject(sql,Integer.class);
		 return result;
		 
	 }
     
}
