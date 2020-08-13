package live.ipso.provider.common.enums;

import lombok.Getter;

/**
 * 逻辑删除标记
 */
@Getter
public enum  DelFlagEnum {
    NORMAL(1, "正常"),
    DELETE(0, "已删除");


    private int code;
    private String msg;

    DelFlagEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
