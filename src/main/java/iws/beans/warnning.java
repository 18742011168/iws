package iws.beans;

import javax.persistence.Entity;

@Entity
public class warnning  {
	   private String warnningId;
	   private String goodId;
	   private String message;
	   
	   public void setWarnningId(String warnningId){
			this.warnningId =warnningId;
		}
		public String getWarnningId (){
			return warnningId;
		}
		
		public void setGoodId(String goodId){
		   	 this.goodId =goodId;
		}
		public String getGoodId(){
		   	 return goodId;
		}
		
		public void setMessage(String message){
			this.message=message;
		}
		public String getMessage (){
			return message;
		}

}
