package test.service;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import test.pojos.Collect;

/**
 * @author Zhangkunji
 * @date 2024/7/27
 * @Description
 */
public interface CollectService {

    SaResult addCollect(Integer aid);

    SaResult uncollect(Integer aid);
}
