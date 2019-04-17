package iws.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class BaseFormAuthenticationFilter extends FormAuthenticationFilter {  
	  
    
	private static final Logger log = LoggerFactory.getLogger(BaseFormAuthenticationFilter.class);  
    
    @Override  
    protected boolean isAccessAllowed(ServletRequest request,  
            ServletResponse response, Object mappedValue) {  
        try {  
            // 先判断是否是登录操作  
            if (isLoginSubmission(request, response)) {  
                if (log.isTraceEnabled()) {  
                    log.trace("Login submission detected.  Attempting to execute login.");  
                }  
                return false;  
            }  
        } catch (Exception e) {  
            //log.error(Exceptions.getStackTraceAsString(e));  
        }  
        return super.isAccessAllowed(request, response, mappedValue);  
    }  
  
  
}  
