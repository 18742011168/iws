package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.outOrderDao;
import iws.beans.outOrder;

@Service
public class outOrderService {
	
	@Autowired
	private outOrderDao outorderdao;
	
	public boolean updateoutorder(String orderId) {
		 return outorderdao.updateorder(orderId);
	}
	
	public boolean deleteoutorder(String orderId) {
		 return outorderdao.deleteorder(orderId);
	 }
	
	public boolean addoutorder (outOrder outorder ) {
		return outorderdao.addoutorder(outorder);
	}
	
	public List<outOrder> alloutorder(){
		return outorderdao.alloutorder();
	}
	

	
	

}
