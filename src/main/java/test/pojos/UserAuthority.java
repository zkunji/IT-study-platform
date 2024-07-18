package test.pojos;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_authority")
public class UserAuthority {

    private Long uid;
    private String authority;
    private String role;

    public UserAuthority(Long uid, String authority, String role) {
        this.uid = uid;
        this.authority = authority;
        this.role = role;
    }
}
