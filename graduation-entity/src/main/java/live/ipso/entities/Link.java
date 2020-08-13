package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("link")
@AllArgsConstructor
@NoArgsConstructor
public class Link extends Base implements Serializable {
    @TableId
    private Long linkId;

    @NotNull
    private String linkName; // 友情链接备注

    @NotNull
    private Long userId;

    @NotNull
    private String address; // 友情链接所属会员
    private Integer isShow; // 是否对外显示，0：不显示；1显示
    private Integer type; // 链接类型，0：标签；1：友情链接
}
