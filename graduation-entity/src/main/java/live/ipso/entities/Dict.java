package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("dict")
@AllArgsConstructor
@NoArgsConstructor
public class Dict extends Base implements Serializable {

    @TableId
    private Long dictId;

    @NotNull
    private String dictName;
}
