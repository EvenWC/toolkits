package priv.wangcheng.zeus.security.browser.session;

import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
/**
 * @author wangcheng
 * @version $Id: ZeusExpiredSessionStrategy.java, v0.1 2019/5/26 17:55 wangcheng Exp $$
 */
public class ZeusExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public ZeusExpiredSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(), event.getResponse());
	}
	
	@Override
	protected boolean isConcurrency() {
		return true;
	}

}
