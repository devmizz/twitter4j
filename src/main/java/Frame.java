import twitter4j.PagableResponseList;
import twitter4j.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Frame extends JFrame {

    TwitterHandler twitter = new TwitterHandler();
    PagableResponseList<User> friends = twitter.getFriends();   // 팔로우 목록

    int count = 0;
    int friendsCount = friends.size();                          // 팔로우 수

    JPanel p;
    JLabel imageLabel;
    JLabel name;
    JButton beforeButton;
    JButton afterButton;
    JLabel follower;
    JLabel friend;
    JLabel uri;


    public Frame() throws Exception {
        init();
        listener();
    }

    private void init() {
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("follower list");
        setResizable(false);

        p = new JPanel();
        p.setLayout(null);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(twitter.getImageUrl(friends.get(count))));
            imageLabel = new JLabel(new ImageIcon(image));
            imageLabel.setBounds(150, 0, 100, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }

        beforeButton = new JButton("before");
        beforeButton.setBounds(0, 80, 80, 40);

        afterButton = new JButton("after");
        afterButton.setBounds(320, 80, 80, 40);

        name = new JLabel(friends.get(count).getName());
        name.setBounds(100, 100, 200, 30);

        follower = new JLabel("follower : " + friends.get(count).getFollowersCount());
        follower.setBounds(100, 130, 200, 30);

        friend = new JLabel("following : " + friends.get(count).getFriendsCount());
        friend.setBounds(100, 160, 200, 30);

        uri = new JLabel("프로필로 이동");
        uri.setForeground(Color.BLUE);
        uri.setBounds(100, 190, 200, 30);
        uri.setCursor(new Cursor(Cursor.HAND_CURSOR));

        p.add(beforeButton);
        p.add(imageLabel);
        p.add(name);
        p.add(afterButton);
        p.add(follower);
        p.add(friend);
        p.add(uri);

        this.add(p);

        setVisible(true);
    }

    public void listener() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(beforeButton) && count > 0) {
                    count -= 1;
                    updateUi();
                }
                if (e.getSource().equals(afterButton) && count < friendsCount) {
                    count += 1;
                    updateUi();
                }
            }
        };

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://twitter.com/" + friends.get(count).getScreenName()));
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        beforeButton.addActionListener(listener);
        afterButton.addActionListener(listener);
        uri.addMouseListener(mouseListener);
    }

    private void updateUi() {
        BufferedImage image;
        try {
            image = ImageIO.read(new URL(twitter.getImageUrl(friends.get(count))));
            imageLabel.setIcon(new ImageIcon(image));
            name.setText(friends.get(count).getName());
            follower.setText("follower : " + friends.get(count).getFollowersCount());
            friend.setText("following : " + friends.get(count).getFriendsCount());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
