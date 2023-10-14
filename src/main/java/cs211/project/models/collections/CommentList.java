package cs211.project.models.collections;

import cs211.project.models.event.Comment;

import java.util.ArrayList;

public class CommentList {

    private ArrayList<Comment> commentList;
    public CommentList(){
        commentList = new ArrayList<>();
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void addComment(String teamName, String comment, String eventName){
        commentList.add(new Comment(teamName, comment, eventName));
    }
}
