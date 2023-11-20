package cs211.project.models.collections;

import cs211.project.models.event.Comment;

import java.util.ArrayList;

public class CommentList {

    private ArrayList<Comment> commentList;

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public CommentList() {
        commentList = new ArrayList<>();
    }

    public void addComment(String teamName, String comment, String eventName, String username) {
        commentList.add(new Comment(teamName, comment, eventName, username));
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }


    public CommentList findComment(String name) {
        CommentList commentTemp = new CommentList();
        for (Comment comment : commentList) {
            if (comment.checkTeamname(name)) {
                commentTemp.addComment(comment);
            }
        }
        return commentTemp;
    }
}
