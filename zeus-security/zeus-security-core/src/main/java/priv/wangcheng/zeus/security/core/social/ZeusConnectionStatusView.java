/**
 * 
 */
package priv.wangcheng.zeus.security.core.social;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.web.servlet.view.AbstractView;
import priv.wangcheng.common.utils.JsonUtils;

/**
 * @author wangcheng
 * @version $Id: ZeusConnectionStatusView.java, v0.1 2019/5/26 12:39 wangcheng Exp $$
 */
public class ZeusConnectionStatusView extends AbstractView {
	

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");
		
		Map<String, Boolean> result = new HashMap<>();
		for (String key : connections.keySet()) {
			result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
		}
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getWriter().write(JsonUtils.writeValueAsString(result));
	}
}
