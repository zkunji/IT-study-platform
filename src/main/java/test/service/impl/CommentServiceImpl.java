package test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.mapper.CommentMapper;
import test.pojos.Comment;
import test.result.Result;
import test.service.CommentService;
import test.utils.TokenParseUtil;

import java.time.LocalDateTime;



//回复评论功能开发中




@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result addComment(Comment comment, Integer aid) {
        Integer uid = TokenParseUtil.getUID();
        comment.setUid(uid);
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
        comment.eq(Comment::getCid, cid);
        int delete = commentMapper.delete(comment);
        if (delete > 0) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    @Override
    public Result likeNumber(Integer cid, Integer number) {
        LambdaUpdateWrapper<Comment> comment = new LambdaUpdateWrapper<>();
        comment.set(Comment::getLikeNumber, number);
        boolean update = commentMapper.update(comment) > 0;
        if (update) {
            return Result.success(null);
        }
        return Result.fail("更新点赞数失败");
    }
}
