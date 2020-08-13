package live.ipso.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("notice")
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends Base implements Serializable {

    @TableId
    private Long noticeId;

    @NotNull
    private String noticeName; // 友情链接备注

    @NotNull
    private Long userId;
    private String content; // 通知内容
}
