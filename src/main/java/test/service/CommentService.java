package test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.pojos.Comment;
import test.result.Result;

public interface CommentService extends IService<Comment> {
    Result addComment(Comment comment, Integer aid);

    Result deleteComment(Integer cid);

    Result likeNumber(Integer cid, Integer number);
}
