package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("picture")
@AllArgsConstructor
@NoArgsConstructor
public class Picture extends Base implements Serializable {

    @TableId
    private Long pictureId;

    @NotNull
    private String pictureName; // 相片名

    @NotNull
    private Long albumId;  // 相册id
    private Integer isShow; // 图片是否显示，0：不显示；1：显示
    private String picture; // 图片地址
    private String intro; // 图片简介
}
