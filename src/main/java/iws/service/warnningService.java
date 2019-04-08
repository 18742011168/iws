package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iws.DAO.warnningDao;
import iws.beans.warnning;;
@Service
public class warnningService {
	
	@Autowired
	private warnningDao warnningdao;
	
	public List<warnning> allwaring(){
		return warnningdao.allwaring();
	}
	
	public boolean addwarnning(warnning warn) {
		return warnningdao.addwarnning(warn);
	}
	
	public int warnningnumber() {
		return warnningdao.warnningnumber();
	}
}
