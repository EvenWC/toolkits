/**
 * 
 */
package priv.wangcheng.zeus.security.core.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import priv.wangcheng.zeus.security.core.code.ValidateCode;


/**
 * @author wangcheng
 * @version $Id: AbstractChannelSecurityConfiguration.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class ImageCode extends ValidateCode {
	
	private BufferedImage image; 
	
	public ImageCode(BufferedImage image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
