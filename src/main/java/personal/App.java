package personal;

import personal.viewcontroller.InsertNewUser;
import personal.viewcontroller.MainMenuFrame;
import personal.viewcontroller.VotingWindow;

import java.awt.EventQueue;

public class App {
    private final static MainMenuFrame mainMenuFrame = new MainMenuFrame();
    private final static InsertNewUser insertNewUser = new InsertNewUser();
    private final static VotingWindow votingWindow = new VotingWindow();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    mainMenuFrame.setLocationRelativeTo(null);
                    mainMenuFrame.setVisible(true);

                    insertNewUser.setLocationRelativeTo(null);
                    insertNewUser.setVisible(false);

                    votingWindow.setLocationRelativeTo(null);
                    votingWindow.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static MainMenuFrame getMainMenuFrame() {
        return mainMenuFrame;
    }

    public static InsertNewUser getInsertNewUser() {
        return insertNewUser;
    }

    public static VotingWindow getVotingWindow() {
        return votingWindow;
    }
}
