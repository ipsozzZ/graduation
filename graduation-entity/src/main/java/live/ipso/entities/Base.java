package live.ipso.entities;

import lombok.Data;

import javax.tools.Tool;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
public class Base implements Serializable {

    // 删除标记
    private Integer delFlag;

    // 字段概要
    private String remark;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;


    /**
     * 初始化更新
     */
    public void setUpdatde(){
        this.delFlag = 1;
        this.setCreateTime(null);
        this.setUpdateTime(Calendar.getInstance().getTime());
    }

    /**
     * 初始化插入
     */
    public void setCreate(){
        this.delFlag = 1;
        this.setCreateTime(Calendar.getInstance().getTime());
        this.updateTime = null;
    }

    /**
     * 将所有字段置空
     */
    public void setBaseNull(){
        this.updateTime = null;
        this.remark = null;
        this.createTime = null;
        this.delFlag = null;
    }

    /**
     * 格式化创建日期和更新日期
     */
    public void changeDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日 E HH 点 mm 分 ss 秒");
        format.format(this.updateTime);
        format.format(this.createTime);
    }

}
