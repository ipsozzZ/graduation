package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("contact")
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends Base implements Serializable {

    @TableId
    private Long contactId;

    @NotNull
    private String contactName; // 备注名
    private String relationship; // 人物关系

    @NotNull
    private Long cateId; // 分类id
    private String phone; // 电话

    @NotNull
    private Long userId; // 用户id
    private String email; // email
    private String front; // 联系人头像，用户扩展
    private String home; // 联系人个人站点
}
