package iws.DAO;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;


import iws.beans.outOrder;

@Component
public class outOrderDao extends orderDao{
	/*
	public List<outOrder> alloutorder(){
		String type="出库";
		String sql="select orders.orderId,goodId,preWarehouseId,type,state "
				+ "from orders join order_goods on orders.orderId=order_goods.orderId "
				+ "where orders.type='"+type+"'";
		List<outOrder> outorderlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<outOrder>(outOrder.class));
		return outorderlist;
	}
	*/
	public List<outOrder> alloutorder(){
		String type="出库";
		String sql="select orderId,preWarehouseId,type,state from orders where orders.type='"+type+"'";				
		List<outOrder> outorderlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<outOrder>(outOrder.class));
		return outorderlist;
	}
	
	public boolean addoutorder(outOrder outorder ) {
		String sql1="insert into orders(orderId,preWarehouseId,type,state) values(?,?,?,?)";
		String sql2="insert into order_goods(orderId,goodId) values(?,?)";
		
	    int number1=jdbcTemplate.update(sql1,new Object[]{outorder.getOrderId(),outorder.getPreWarehouseId(),outorder.getType(),outorder.getState()});
	    int number2=jdbcTemplate.update(sql2,new Object[]{outorder.getOrderId(),outorder.getGoodId()});
	    return number1==number2;
	}
	
	public boolean addorder_goods(outOrder outorder) {
		String sql="insert into order_goods(orderId,goodId) values(?,?)";
		return jdbcTemplate.update(sql,new Object[]{outorder.getOrderId(),outorder.getGoodId()})==1;
	}
	public List<outOrder> findById(String orderId){
		String type="出库";
		String sql="select orderId,preWarehouseId,type,state from orders "
				+ "where type='"+type+"' and orderId='"+orderId+"'";
				
		List<outOrder> outorderlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<outOrder>(outOrder.class));
		return outorderlist;
	}
	
	public int outordernumber() {
		 String type="出库";
		 String sql="select count(*) from orders "
		 		+ "where type='"+type+"'";				 
		 int result=jdbcTemplate.queryForObject(sql,Integer.class);
		 return result;
		 
	 }

}
