/**
 * 
 */
package priv.wangcheng.zeus.security.core.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangcheng
 * @version $Id: ValidateCode.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class ValidateCode implements Serializable {

	private static final long serialVersionUID = -1L;

	private String code;
	
	private LocalDateTime expireTime;
	
	public ValidateCode(String code, int expireIn){
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	public ValidateCode(String code, LocalDateTime expireTime){
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public ValidateCode(){
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
	
}
