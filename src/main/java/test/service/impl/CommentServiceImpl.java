package test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.mapper.CommentMapper;
import test.pojos.Comment;
import test.result.Result;
import test.service.CommentService;
import test.utils.ThreadLocalUtil;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result addComment(Comment comment ,Integer aid) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        comment.setId(uid);
        comment.setArticleId(aid);
        comment.setReleaseTime(LocalDateTime.now());
        boolean res = commentMapper.insert(comment) > 0;
        if (res) {
            return Result.success(comment);
        } else {
            return Result.fail("评论发表失败");
        }
    }

    @Override
    public Result deleteComment(Integer cid) {
        LambdaQueryWrapper<Comment> comment = new LambdaQueryWrapper<>();
        comment.eq(Comment::getId,cid);
        int delete = commentMapper.delete(comment);
        if (delete>0){
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    @Override
    public Result likeNumber(Integer cid, Integer number) {
        LambdaQueryWrapper<Comment> comment = new LambdaQueryWrapper<>();
        comment.eq(Comment::getId,cid);
        boolean update = commentMapper.update(comment) > 0;
        if (update){
            return Result.success(null);
        }
        return Result.fail("更新点赞数失败");
    }
}
