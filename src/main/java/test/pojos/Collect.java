package test.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Zhangkunji
 * @date 2024/7/27
 * @Description 收藏类
 */

@Data
@TableName("collect")
public class Collect {
    private Integer aid;
    private Integer uid;

}
