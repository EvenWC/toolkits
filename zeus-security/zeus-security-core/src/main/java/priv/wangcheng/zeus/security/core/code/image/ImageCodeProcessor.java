/**
 * 
 */
package priv.wangcheng.zeus.security.core.code.image;

import java.util.Map;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import priv.wangcheng.zeus.security.core.code.ValidateCodeGenerator;
import priv.wangcheng.zeus.security.core.code.ValidateCodeRepository;
import priv.wangcheng.zeus.security.core.code.impl.AbstractValidateCodeProcessor;

/**
 * @author wangcheng
 * @version $Id: ImageCodeProcessor.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	private static final String IMAGE_TYPE_JPEG = "JPEG";

	public ImageCodeProcessor(
			Map<String, ValidateCodeGenerator> validateCodeGenerators,
			ValidateCodeRepository validateCodeRepository) {
		super(validateCodeGenerators, validateCodeRepository);
	}

	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		ImageIO.write(imageCode.getImage(), IMAGE_TYPE_JPEG, request.getResponse().getOutputStream());
	}
}
