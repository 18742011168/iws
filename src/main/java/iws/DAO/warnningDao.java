package iws.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import iws.beans.warnning;
@Service
public class warnningDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<warnning> allwaring(){
		String sql="select * from warnning";
		List<warnning> warnniglist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<warnning>(warnning.class));
		return warnniglist;
	}
	
	public boolean addwarnning(warnning warn) {
		String sql="insert into warnning(goodId,message) values(?,?)";
		return jdbcTemplate.update(sql, new Object[] {warn.getGoodId(),warn.getMessage()})==1;
	}
	
	public int warnningnumber() {
		 String sql="select count(*) from warnning";
		 int result=jdbcTemplate.queryForObject(sql,Integer.class);
		 return result;
		 
	 }
}
