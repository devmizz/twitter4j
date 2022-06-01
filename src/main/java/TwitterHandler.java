import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class TwitterHandler {

    Twitter twitter = TwitterFactory.getSingleton();

    String screenName;

    {
        try {
            screenName = twitter.getScreenName();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public String getImageUrl(User user) {
        return user.getProfileImageURL();
    }

    public PagableResponseList<User> getFriends() throws TwitterException {
        return twitter.getFriendsList(twitter.getScreenName(), -1);
    }

    public int getFriendCount() throws TwitterException {
        return twitter.getFriendsList(twitter.getScreenName(), -1).size();
    }
}
