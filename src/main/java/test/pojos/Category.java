package test.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("category")
public class Category {
    @TableId(type = IdType.AUTO)
    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty(groups = {Add.class, Update.class})
    private String categoryName;
    @NotEmpty(groups = {Add.class, Update.class})
    private String categoryAlias;
    private Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime= LocalDateTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime ;

    public interface Add extends Default {

    }

    public interface Update extends Default {

    }
}
