package test.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@TableName("user")
public class User{

    @TableId(type = IdType.AUTO)
    private Long uid;
    private String account;
    private String username;
    private String password;
    private String email;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime = LocalDateTime.now();
    private String userAvatar;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }




}
