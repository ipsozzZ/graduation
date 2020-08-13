package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("new_service")
@AllArgsConstructor
@NoArgsConstructor
public class NewService extends Base implements Serializable {

    @TableId
    private Long newId;

    @NotNull
    private String newName;

    private Integer likeNum;
    private Integer unlikeNum;
    private Integer state;
    private String content;
}
