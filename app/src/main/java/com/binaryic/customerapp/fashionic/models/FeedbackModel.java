package com.binaryic.customerapp.fashionic.models;

/**
 * Created by Asd on 27-09-2016.
 */
public class FeedbackModel {
    String id,user_image,user_name,feedback_message,feedback_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getFeedback_message() {
        return feedback_message;
    }

    public void setFeedback_message(String feedback_message) {
        this.feedback_message = feedback_message;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFeedback_image() {
        return feedback_image;
    }

    public void setFeedback_image(String feedback_image) {
        this.feedback_image = feedback_image;
    }
}
