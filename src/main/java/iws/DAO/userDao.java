package iws.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import iws.beans.user;
@Component
public class userDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	 public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		 this.jdbcTemplate = jdbcTemplate;
	 }
	 
	 public List<user> queryAll(){
		 String sql="select * from users";
	     List <user> userlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<user>(user.class));
	     return userlist;
	 }
	 
	 public List<user> FindUserByName(String username){
		 String sql="select * from users where username='"+username+"'";
		 List <user> userlist=jdbcTemplate.query(sql, new BeanPropertyRowMapper<user>(user.class));
	     return userlist;
	 }
	 
	 public boolean adduser(user user){
		 String sql="insert into users(username,password,position,email) values(?,?,?,?)";
		 return jdbcTemplate.update(sql,new Object[]{user.getUsername(),user.getPassword(),user.getPosition(),user.getEmail()})==1;
	 }
	 
	 public boolean deleteuser(String username){
		 String sql="delete from users where username=?";
		 return jdbcTemplate.update(sql,username)==1;
	 }
	 
	 public boolean updateuser(user user){
		 String sql="update users set password=?,position=?,email=? where username=?";
		 return jdbcTemplate.update(sql,new Object[]{user.getPassword(),user.getPosition(),user.getEmail(),user.getUsername()})==1;
	 }
	 
	 public boolean resetverification(String username,String verification) {
		 String sql="update users set verification=? where username=?";
		 return jdbcTemplate.update(sql,new Object[] {verification,username})==1;
	 }
	 
	 public boolean resetpassword(String username,String password) {
		 String sql="update users set password=? where username=?";
		 return jdbcTemplate.update(sql,new Object[] {password,username})==1;
	 }
	 
	 public int usernumber() {
		 String sql="select count(*) from users";
		 int result=jdbcTemplate.queryForObject(sql,Integer.class);
		 return result;
		 
	 }
	 
	 public int managernumber() {
		 String sql="select count(*) from users where position='manager'";
		 int result=jdbcTemplate.queryForObject(sql,Integer.class);
		 return result;
	 }
	 
	 public int financenumber() {
		 String sql="select count(*) from users where position='finance'";
		 int result=jdbcTemplate.queryForObject(sql,Integer.class);
		 return result;
	 }
	 
	 public int godownnernumber() {
		 String sql="select count(*) from users where position='godownner'";
		 int result=jdbcTemplate.queryForObject(sql,Integer.class);
		 return result;
	 }
	 
}
