package test.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import test.anno.State;

import java.time.LocalDateTime;

@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty(groups = {Article.Add.class, Article.Update.class})

    private String title;
    @NotEmpty(groups = Article.Add.class)
    private String content;
    @NotEmpty(groups = {Article.Add.class, Article.Update.class})

    private String coverImg;
    @State
    private String state;
    @NotEmpty(groups = {Article.Add.class, Article.Update.class})
    private Integer categoryId;
    //@NotEmpty(groups = {Article.Add.class, Article.Update.class})
    private Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public interface Add extends Default {

    }

    public interface Update extends Default {

    }
}
