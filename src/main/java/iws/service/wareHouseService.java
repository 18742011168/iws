package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.wareHouseDao;
import iws.beans.wareHouse;

@Service
public class wareHouseService {
	@Autowired
	private wareHouseDao warehousedao;
	
	public List<wareHouse> allwarehouse(){
		return warehousedao.allwarehouse();
	}
    
}
