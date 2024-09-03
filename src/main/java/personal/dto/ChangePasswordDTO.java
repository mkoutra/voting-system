package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring data related to a password change request
 * from the view layer to the service layer.
 */
public class ChangePasswordDTO {
    private String currentPassword;
    private String newPassword;
    private String reEnteredPassword;

    public ChangePasswordDTO() {}

    public ChangePasswordDTO(String currentPassword, String newPassword, String reEnteredPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.reEnteredPassword = reEnteredPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReEnteredPassword() {
        return reEnteredPassword;
    }

    public void setReEnteredPassword(String reEnteredPassword) {
        this.reEnteredPassword = reEnteredPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", reEnteredPassword='" + reEnteredPassword + '\'' +
                '}';
    }
}
