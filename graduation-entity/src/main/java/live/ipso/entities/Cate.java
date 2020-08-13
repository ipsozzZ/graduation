package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("cate")
@AllArgsConstructor
@NoArgsConstructor
public class Cate extends Base implements Serializable {

    @TableId
    private Long cateId;

    @NotNull
    private String cateName;
    private Long dictId;
    private Long userId;
}
