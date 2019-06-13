package iws.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import iws.beans.goods;
import iws.beans.wareHouse;

@Component
public class wareHouseDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<wareHouse> allwarehouse(){
		String sql="select * from wareHouse";
		List<wareHouse> warehouselist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<wareHouse>(wareHouse.class));
		return warehouselist;
	}
	
	public List<wareHouse> findbyId(String wareHouseId){
		
		String sql="select * from wareHouse where wareHouseId='"+wareHouseId+"'";
		List<wareHouse> warehouselist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<wareHouse>(wareHouse.class));
		return warehouselist;
	}
	
	public List<wareHouse> findbyKind1(String kind){
		String sql="select * from wareHouse where kind='"+kind+"'";
		List<wareHouse> warehouselist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<wareHouse>(wareHouse.class));
		return warehouselist;
	}
	
	public List<wareHouse> findbyKind2(String kind){
		String sql="select * from wareHouse where kind='"+kind+"' and inventory>0";
		List<wareHouse> warehouselist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<wareHouse>(wareHouse.class));
		return warehouselist;
	}
	public boolean addwarehouse(wareHouse warehouse) {
		 String sql="insert into wareHouse(wareHouseId,volume,inventory) values(?,?,?)";
		 return jdbcTemplate.update(sql,new Object[]{warehouse.getWareHouseId(),warehouse.getVolume(),warehouse.getInventory()})==1;
	}
	
	public boolean deletewarehouse(String wareHouseId) {
		 String sql="delete from wareHouse where wareHouseId=?";
		 return jdbcTemplate.update(sql,wareHouseId)==1;
	}
	
	
	public List<goods> findgoods(String warehouseid) {
		String sql="select * from goods where wareHouseId='"+warehouseid+"'";
		List<goods> goodslist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
	
	public boolean updatewarehouse(String wareHouseId,int inventory) {
		String sql="update warehouse set inventory=? where wareHouseId=?";
		return jdbcTemplate.update(sql,new Object[]{inventory,wareHouseId})==1;
	}
	
	public int warehousenumber() {
		 String sql="select count(*) from warehouse";
		 int result=jdbcTemplate.queryForObject(sql,Integer.class);
		 return result;
		 
	 }
	


}
