package test.service.impl;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.mapper.CollectMapper;
import test.pojos.Collect;
import test.service.CollectService;
import test.utils.TokenParseUtil;

/**
 * @author Zhangkunji
 * @date 2024/7/27
 * @Description
 */

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public SaResult addCollect(Integer aid) {
        Integer uid = TokenParseUtil.getUID();
        Collect collect = new Collect();
        collect.setAid(aid);
        collect.setUid(uid);
        boolean res = collectMapper.insert(collect) > 0;
        if (res) {
            return SaResult.ok("收藏成功");
        }
        return SaResult.error("收藏失败");
    }

    @Override
    public SaResult uncollect(Integer aid) {
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Collect::getAid, aid);
        boolean res = collectMapper.delete(lqw) > 0;
        if (res) {
            return SaResult.ok("取消收藏");
        }
        return SaResult.error("取消收藏失败");
    }
}
