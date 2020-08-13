package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("album")
@AllArgsConstructor
@NoArgsConstructor
public class Album extends Base implements Serializable {

    @TableId
    private Long albumId;

    @NotNull
    private String albumName;

    @NotNull
    private Long userId;
    private String albumIntro;
    private Integer albumType;
}
