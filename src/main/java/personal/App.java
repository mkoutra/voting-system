package personal;

import personal.viewcontroller.*;

import java.awt.EventQueue;

/**
 * @author Michail E. Koutrakis
 */
public class App {
    private final static MainMenuFrame mainMenuFrame = new MainMenuFrame();
    private final static InsertNewUserFrame insertNewUserFrame = new InsertNewUserFrame();
    private final static VotingFrame votingFrame = new VotingFrame();
    private final static VotingResultsFrame votingResultsFrame = new VotingResultsFrame();
    private final static CandidatesFrame candidatesFrame = new CandidatesFrame();
    private final static ChangePasswordFrame changePasswordFrame = new ChangePasswordFrame();
    private final static AdminOptionsFrame adminOptionsFrame = new AdminOptionsFrame();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    mainMenuFrame.setLocationRelativeTo(null);
                    mainMenuFrame.setVisible(true);

                    adminOptionsFrame.setLocationRelativeTo(null);
                    adminOptionsFrame.setVisible(false);

                    insertNewUserFrame.setLocationRelativeTo(null);
                    insertNewUserFrame.setVisible(false);

                    votingFrame.setLocationRelativeTo(null);
                    votingFrame.setVisible(false);

                    votingResultsFrame.setLocationRelativeTo(null);
                    votingResultsFrame.setVisible(false);

                    candidatesFrame.setLocationRelativeTo(null);
                    candidatesFrame.setVisible(false);

                    changePasswordFrame.setLocationRelativeTo(null);
                    changePasswordFrame.setVisible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static MainMenuFrame getMainMenuFrame() {
        return mainMenuFrame;
    }

    public static InsertNewUserFrame getInsertNewUser() {
        return insertNewUserFrame;
    }

    public static VotingFrame getVotingWindow() {
        return votingFrame;
    }

    public static VotingResultsFrame getVotingResultsFrame() {
        return votingResultsFrame;
    }

    public static CandidatesFrame getCandidatesFrame() {
        return candidatesFrame;
    }

    public static ChangePasswordFrame getChangePasswordFrame() {
        return changePasswordFrame;
    }

    public static AdminOptionsFrame getAdminOptionsFrame() { return adminOptionsFrame; }
}
