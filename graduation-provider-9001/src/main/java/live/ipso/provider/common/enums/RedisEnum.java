package live.ipso.provider.common.enums;

import lombok.Getter;

/**
 * redis枚举类
 */
@Getter
public enum RedisEnum {

    REFRESH_User_PREFIX("refresh token的前缀", "user:refresh:"),
    REFRESH_TOKEN_PREFIX("refresh token的前缀", "token:refresh:"),
    SHORTMESSAGE_CACHE_PREFIX("短信验证码缓存的key值", "short_message:cache:");
    private String name;
    private String code;

    RedisEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
