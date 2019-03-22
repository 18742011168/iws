package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.inOrderDao;
import iws.beans.inOrder;

@Service
public class inOrderService {
	@Autowired
	private inOrderDao inorderdao;
	
	 public boolean updateinorder(String orderId) {
		 return inorderdao.updateorder(orderId);
	 }
	 
	 public boolean deleteinorder(String orderId) {
		 return inorderdao.deleteorder(orderId);
	 }
	 
	 public boolean addinorder(inOrder inorder) {
			
		 return inorderdao.addinorder(inorder);	 
     }
	 
	 public List<inOrder> allinorder(){
		 return inorderdao.allinorder();
	 }

}
