import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTests {
    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setPostID(1);
        post.setPostTitle("Valid Title");
        post.setPostBody("This is a valid post body that is definitely more than 250 characters long. " +
                "This is a valid post body that is definitely more than 250 characters long. " +
                "This is a valid post body that is definitely more than 250 characters long. " +
                "This is a valid post body that is definitely more than 250 characters long. ");
        post.setPostTags(new String[]{"tagone", "tagtwo"});
        post.setPostType("Difficult");
        post.setPostEmergencyStatus("Highly Needed");
    }

    /** Tests for adding posts
     *
     */
    @Test
    void testAddValidPost() {
        assertTrue(post.addPost());
    }

    @Test
    void testInvalidPostTitle() {
        post.setPostTitle("Short");
        assertFalse(post.addPost());
    }

    @Test
    void testInvalidPostBodyLength() {
        post.setPostBody("Short body");
        assertFalse(post.addPost());
    }

    @Test
    void testInvalidPostType() {
        post.setPostType("Invalid Type");
        assertFalse(post.addPost());
    }

    @Test
    void testInvalidPostTagsEasy() {
        post.setPostType("Easy");
        post.setPostTags(new String[]{"tag1", "tag2", "tag3", "tag4"});
        assertFalse(post.addPost());
    }

    @Test
    void testInvalidPostBodyForDifficult() {
        post.setPostType("Very Difficult");
        post.setPostBody("Short body");
        assertFalse(post.addPost());
    }

    @Test
    void testInvalidPostEmergencyStatusForEasy() {
        post.setPostType("Easy");
        post.setPostEmergencyStatus("Immediately Needed");
        assertFalse(post.addPost());
    }

    @Test
    void testInvalidPostEmergencyStatusForDifficultTypes() {
        post.setPostType("Very Difficult");
        post.setPostEmergencyStatus("Ordinary");
        assertFalse(post.addPost());
    }

    /** Tests for comments
     *
     */
    @Test
    void testAddValidComment() {
        String comment = "This is a valid comment.";
        assertTrue(post.addComment(comment));
    }

    @Test
    void testInvalidCommentLength() {
        String shortComment = "Too short";
        String longComment = "This comment actually has way too many words to be valid.";
        assertFalse(post.addComment(shortComment));
        assertFalse(post.addComment(longComment));
    }

    @Test
    void testInvalidCommentFirstCharacter() {
        String comment = "this is an invalid comment.";
        assertFalse(post.addComment(comment));
    }

    @Test
    void testTooManyComments() {
        for (int i = 0; i < 5; i++) {
            post.addComment("This is a valid comment " + (i + 1) + ".");
        }
        assertFalse(post.addComment("This should fail."));
    }

    @Test
    void testTooManyCommentsForEasyOrdinary() {
        post.setPostType("Easy");
        post.setPostEmergencyStatus("Ordinary");
        for (int i = 0; i < 3; i++) {
            post.addComment("This is a valid comment " + (i + 1) + ".");
        }
        assertFalse(post.addComment("This should fail."));
    }

    @Test
    void testInvalidCommentSpecialCharacterFirstCharacter() {
        String comment = "@Invalid comment with special character.";
        assertFalse(post.addComment(comment));
    }
}
