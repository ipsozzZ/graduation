package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends Base implements Serializable {

    @TableId
    private Long commentId;

    @NotNull
    private String commentName;
    private Long userId;
    private Integer levelNum; // 评论分级，0：主评或者留言；1：子评论或者留言评论
    private Integer likeNum; // 评论点赞数
    private Long parentId; // 父评论id或者留言id
    private Long articleId; // 文章id
    private String content; // 文章内容
}
