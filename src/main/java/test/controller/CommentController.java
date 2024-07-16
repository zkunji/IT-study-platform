package test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.pojos.Comment;
import test.result.Result;
import test.service.CommentService;

@RestController("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Result addComment(@RequestBody Comment comment, Integer aid) {
        return commentService.addComment(comment, aid);
    }

    @PostMapping("/deleteComment")
    public Result deleteComment(Integer cid) {
        return commentService.deleteComment(cid);
    }

}
