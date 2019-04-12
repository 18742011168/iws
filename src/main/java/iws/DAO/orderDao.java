package iws.DAO;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import iws.beans.changeOrder;

import iws.beans.order;



@Component
public class orderDao {
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	 public boolean deleteorder(String orderId){
		 String sql="delete from orders where orderId=?";
		return (jdbcTemplate.update(sql,orderId)==1);
	 }
	 
	 public boolean updateorder(String orderId,String state) {
		 String sql="update orders set state=? where orderId=?";
		 return(jdbcTemplate.update(sql,new Object[] {state,orderId})==1);
	 }
	 
	 public List<changeOrder> allorder(){
			String sql="select orders.orderId,goodId,preWarehouseId,nextWarehouseId,type,state "
					+ "from from orders join order_goods on orders.orderId=order_goods.orderId ";
					
			List<changeOrder> orderlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<changeOrder>(changeOrder.class));
			return orderlist;
		}
	 
	 public List<changeOrder> findOrder(String orderId){
			
			String sql="select orderId,preWarehouseId,nextWarehouseId,type,state from orders "
					+ "where orderId='"+orderId+"'";
					
			List<changeOrder> changeorderlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<changeOrder>(changeOrder.class));
			return changeorderlist;
		}
	 
	 //下发订单时查看订单号是否冲突
	 public boolean hasorder(String orderId) {
		 String sql="select orderId,type,state from orders where orderId='"+orderId+"'";
					
		 List<order> orderlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<order>(order.class));
		 return !orderlist.isEmpty();
	 }
	 
	 public boolean hasorder_goods(String orderId,String goodId) {
		 String sql="select * from order_goods where orderId='"+orderId+"' and goodId='"+goodId+"'";
		 List<order> orderlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<order>(order.class));
		 return !orderlist.isEmpty();
	 }
	 
	 public boolean deleteordergoods(String orderId,String goodId) {
		 String sql="delete from order_goods where orderId=? and goodId=?";
		 return (jdbcTemplate.update(sql,orderId,goodId)==1);
	 }

}
