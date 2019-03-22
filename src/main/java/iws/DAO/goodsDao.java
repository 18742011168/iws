package iws.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import iws.beans.goods;


@Component
public class goodsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<goods> allgoods(){
		String sql="select * from goods";
		List<goods> goodslist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
	
	public List<goods> findgoods(String goodId) {
		String sql="select * from goods where goodId='"+goodId+"'";
		List<goods> goodslist=jdbcTemplate.query(sql,new BeanPropertyRowMapper<goods>(goods.class));
		return goodslist;
	}
	
	public boolean addgoods(goods goods) {
		String sql="insert into goods(goodId,category,weight,warehouseId,state) values(?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] {goods.getGoodId(),goods.getCategory(),goods.getWeight(),goods.getWarehouseId(),goods.getState()})==1;
	}
	
	public boolean deletegoods(String goodId) {
		String sql="delete from goods where goodId='"+goodId+"'";
		return jdbcTemplate.update(sql)==1;
	}
	/*
	public boolean updategoods(goods goods) {
		
	}
     */
}
