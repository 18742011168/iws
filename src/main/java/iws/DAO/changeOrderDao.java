package iws.DAO;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import iws.beans.changeOrder;


@Component
public class changeOrderDao extends orderDao{
	/*
	 public List<changeOrder> allchangeorder(){
		    String type="位置变更";
			String sql="select orders.orderId,goodId,preWarehouseId,nextWarehouseId,type,state "
					+ "from orders join order_goods on orders.orderId=order_goods.orderId "
					+ "where orders.type='"+type+"'";
			List<changeOrder> changeorderlist=jdbcTemplate.query(sql,new BeanPropertyRowMapper<changeOrder>(changeOrder.class));
			return changeorderlist;
		}
	*/
	 public List<changeOrder> allchangeorder(){
		    String type="位置变更";
			String sql="select orderId,preWarehouseId,nextWarehouseId,type,state from orders where orders.type='"+type+"'";
					
			List<changeOrder> changeorderlist=jdbcTemplate.query(sql,new BeanPropertyRowMapper<changeOrder>(changeOrder.class));
			return changeorderlist;
		}
	 
	public boolean addchangeorder(changeOrder changeorder ) {
		String sql1="insert into orders(orderId,preWarehouseId,nextWarehouseId,type,state) values(?,?,?,?,?,?)";
		int number1=jdbcTemplate.update(sql1,new Object[]{changeorder.getOrderId(),changeorder.getPreWarehouseId(),changeorder.getNextWarehouseId(),changeorder.getType(),changeorder.getState()});
		String sql2="insert into order_goods(orderId,goodId) values(?,?)";
		int number2=jdbcTemplate.update(sql2,new Object[]{changeorder.getOrderId(),changeorder.getGoodId()});
		return number1==number2;
	}
}
