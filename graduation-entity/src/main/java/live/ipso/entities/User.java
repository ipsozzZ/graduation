package live.ipso.entities;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base implements Serializable {

    @TableId
    private Long userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userFront;
    private String userPass;

    private String userHome;
    private String intro;
    private String salt;
    private Integer type;

    @TableField(exist = false)
    private String code;
}
