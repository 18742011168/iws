package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.changeOrderDao;
import iws.beans.changeOrder;


@Service
public class changeOrderService {
	@Autowired
	private changeOrderDao changeorderdao;
	
	public boolean updatechangeorder(String orderId) {
		 return changeorderdao.updateorder(orderId);
	}
	
	public boolean deletechangeorder(String orderId) {
		 return changeorderdao.deleteorder(orderId);
	 }
	
	public boolean addchangeorder(changeOrder changeorder ) {
		return changeorderdao.addchangeorder(changeorder);
	}
	
	 public List<changeOrder> allchangeorder(){
		 return changeorderdao.allchangeorder();
	 }

}
