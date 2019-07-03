package priv.wangcheng.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author wangcheng
 * @version $Id: JsonUtils.java, v0.1 2019/5/27 22:25 wangcheng Exp $$
 */
public class JsonUtils {

    private JsonUtils() throws IllegalAccessException {
        throw new IllegalAccessException("工具类禁止实例化");
    }

    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static <T> T readValue(String src, Class<T> clazz) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        T t = null;
        try {
            t = objectMapper.readValue(src, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage() + " src:{}", src);
        }
        return t;
    }

    public static <T> T readValue(String src, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        T t = null;
        try {
            t = objectMapper.readValue(src, typeReference);
        } catch (IOException e) {
            logger.error(e.getMessage() + " src:{}", src);
        }
        return t;
    }

    public static String writeValueAsString(Object obj) {
        if (null == obj) {
            return null;
        }
        String src = null;
        try {
            src = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return src;
    }
}
