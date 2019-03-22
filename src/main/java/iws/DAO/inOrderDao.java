package iws.DAO;


import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;


import iws.beans.inOrder;

@Component
public class inOrderDao extends orderDao{
	
	public List<inOrder> allinorder(){
		String type="入库";
		String sql="select orders.orderId,goodId,nextWarehouseId,type,state "
				+ "from orders join order_goods on orders.orderId=order_goods.orderId "
				+ "orders.type='"+type+"'";
		List<inOrder> inorderlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<inOrder>(inOrder.class));
		return inorderlist;
	}
	
	public boolean addinorder(inOrder inorder ) {
		String sql1="insert into orders(orderId,nextWarehouseId,type,state) values(?,?,?,?)";
		String sql2="insert into order_goods(orderId,goodId) values(?,?)";
		int number1=jdbcTemplate.update(sql1,new Object[]{inorder.getOrderId(),inorder.getNextWarehouseId(),inorder.getType(),inorder.getState()});
		int number2=jdbcTemplate.update(sql2,new Object[]{inorder.getOrderId(),inorder.getGoodId()});
		return number1==number2;
		
	}

}
 