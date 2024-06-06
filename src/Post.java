import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Post {
    private int postID;
    private String postTitle;
    private String postBody;
    private String[] postTags;
    private final String[] postTypes = {"Very Difficult", "Difficult", "Easy"};
    private String postEmergencyStatus;
    String postType;
    private ArrayList<String> postComments = new ArrayList<>();

    public boolean addPost() {
        // TODO Add the information of a post to a TXT file
        if (!checkCondition1()) {
            System.out.println("Invalid Post Title");
            return false;
        }

        if (!checkCondition2()) {
            System.out.println("Invalid Post Body");
            return false;
        }

        if (!checkCondition3()) {
            System.out.println("Invalid Post Tags");
            return false;
        }

        if (!checkCondition4()) {
            System.out.println("Invalid Post Type");
            return false;
        }

        if (!checkCondition5(postType, postEmergencyStatus)) {
            System.out.println("Invalid Post Emergency Status");
            return false;
        }

        // Write post to the file (post.txt)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("post.txt", true))) {
            writer.write("Post ID: " + postID + "\n");
            writer.write("Post Title: " + postTitle + "\n");
            writer.write("Post Body: " + postBody + "\n");
            writer.write("Post Type: " + postType + "\n");
            writer.write("Post Tags: " + String.join(", ", postTags) + "\n");
            writer.write("Post Emergency Status: " + postEmergencyStatus + "\n");
            writer.write("Post Comments: " + String.join(", ", postComments) + "\n");
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean addComment(String comment) {
        // TODO Add the information of a comment under a post to a TXT file
        // Condition 1: The comment text should have a minimum of 4 words and a maximum of 10 words.
        // The first character of the first word should be an uppercase letter.
        String[] words = comment.split("\\s+");
        if (words.length < 4 || words.length > 10) {
            return false;
        }
        if (!Character.isUpperCase(words[0].charAt(0))) {
            return false;
        }

        // Condition 2: Each post can have 0 to 5 comments.
        // Posts that are "Easy" or "Ordinary" should have a maximum of 3 comments.
        if (postComments.size() >= 5) {
            return false;
        }
        if ((postType.equals("Easy") || postEmergencyStatus.equals("Ordinary")) && postComments.size() >= 3) {
            return false;
        }

        // Add the comment to the postComments list
        postComments.add(comment);

        // Write comment to the file (comment.txt)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("comments.txt", true))) {
            writer.write("Post ID: " + postID + "\n");
            writer.write("Comment: " + comment + "\n");
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean checkCondition1() {
        int postTitleSize = postTitle.length();

        // Return false if the title is less than 10 chars or more than 250 chars
        if (postTitleSize < 10 || postTitleSize > 250) { return false; }

        // Return false if the first 5 chars are NOT letters
        for (int i = 0; i < 5; i++) {
            if (!Character.isLetter(postTitle.charAt(i))) { return false; }
        }

        // If we get to here, condition 1 has been met, thus return true
        return true;
    }

    public boolean checkCondition2() {
        int postBodySize = postBody.length();

        return postBodySize >= 250;
    }

    public boolean checkCondition3() {
        int postTagsSize = postTags.length;

        // If post tag count is less than two or more than 5, return false
        if (postTagsSize < 2 || postTagsSize > 5) {
            System.out.println("Failed post tag count check");
            return false;
        }

        for (String postTag : postTags) {
            // If postTag char count is less than two or more than 10, return false
            if (postTag.length() < 2 || postTag.length() > 10) {
                System.out.println("Failed post tag char count check");
                return false;
            }
            // If postTag chars are not lowercase letters, return false
            for (int i = 0; i < postTag.length(); i++) {
                if (!Character.isLowerCase(postTag.charAt(i))) {
                    System.out.println("Failed post tag lower case char check");
                    return false;
                }
            }
        }

        // If we get to here, condition 2 has been met, thus return true
        return true;
    }

    public boolean checkCondition4() {
        // Ensure the post type is one of the allowed types
        boolean isValidPostType = false;
        for (String type : this.postTypes) {
            if (postType.equals(type)) {
                isValidPostType = true;
                break;
            }
        }
        if (!isValidPostType) {
            return false;
        }

        // Check conditions based on post type
        if (postType.equals("Easy")) {
            // "Easy" posts should not have more than 3 tags
            if (postTags.length > 3) {
                return false;
            }
        } else if (postType.equals("Very Difficult") || postType.equals("Difficult")) {
            // "Very Difficult" and "Difficult" posts should have a minimum of 300 characters in their body
            if (postBody.length() < 300) {
                return false;
            }
        }
        // If we get here, all conditions are met
        return true;
    }


    public boolean checkCondition5(String postType, String postEmergency) {
        // Validate the post emergency status against the post type
        if (postType.equals("Easy")) {
            // "Easy" posts should not have "Immediately Needed" or "Highly Needed" statuses
            if (postEmergency.equals("Immediately Needed") || postEmergency.equals("Highly Needed")) {
                return false;
            }
        } else if (postType.equals("Very Difficult") || postType.equals("Difficult")) {
            // "Very Difficult" and "Difficult" posts should not have the status of "Ordinary"
            if (postEmergency.equals("Ordinary")) {
                return false;
            }
        }

        // If all conditions are met, return true
        return true;
    }

    // Setters, for testing
    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public void setPostTags(String[] postTags) {
        this.postTags = postTags;
    }

    public void setPostEmergencyStatus(String postEmergencyStatus) {
        this.postEmergencyStatus = postEmergencyStatus;
    }
}
