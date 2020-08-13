package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("article")
@AllArgsConstructor
@NoArgsConstructor
public class Article extends Base implements Serializable {

    @TableId
    private Long articleId;

    @NotNull
    private String articleName;

    private String articleCover;
    private Long cateId;
    private Long userId;
    private Integer likeNum;
    private Integer commentNum;
    private String content;
}
