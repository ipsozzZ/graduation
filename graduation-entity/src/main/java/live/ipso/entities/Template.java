package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("template")
@AllArgsConstructor
@NoArgsConstructor
public class Template extends Base implements Serializable {

    @TableId
    private Long templateId;

    @NotNull
    private String templateName; // 标题

    @NotNull
    private Long cateId;  // 分类id
    private Long userId;  // 模板所属用户
    private Integer likeNum;  // 模板点赞数
    private Integer isShow; // 是否显示，0：不显示；1：显示
    private String content; // 模板内容
}
