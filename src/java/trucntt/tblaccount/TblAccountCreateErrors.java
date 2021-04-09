/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblaccount;

/**
 *
 * @author DELL
 */
public class TblAccountCreateErrors {
    private String emailFormatErr;
    private String emailExistedErr;
    private String nameLengthErr;
    private String passwordLengthErr;
    private String confirmNotMatched;

    public TblAccountCreateErrors() {
    }

    public TblAccountCreateErrors(String emailFormatErr, String emailExistedErr, String nameLengthErr, String passwordLengthErr, String confirmNotMatched) {
        this.emailFormatErr = emailFormatErr;
        this.emailExistedErr = emailExistedErr;
        this.nameLengthErr = nameLengthErr;
        this.passwordLengthErr = passwordLengthErr;
        this.confirmNotMatched = confirmNotMatched;
    }

    /**
     * @return the emailFormatErr
     */
    public String getEmailFormatErr() {
        return emailFormatErr;
    }

    /**
     * @param emailFormatErr the emailFormatErr to set
     */
    public void setEmailFormatErr(String emailFormatErr) {
        this.emailFormatErr = emailFormatErr;
    }

    /**
     * @return the nameLengthErr
     */
    public String getNameLengthErr() {
        return nameLengthErr;
    }

    /**
     * @param nameLengthErr the nameLengthErr to set
     */
    public void setNameLengthErr(String nameLengthErr) {
        this.nameLengthErr = nameLengthErr;
    }

    /**
     * @return the passwordLengthErr
     */
    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    /**
     * @param passwordLengthErr the passwordLengthErr to set
     */
    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    /**
     * @return the emailExistedErr
     */
    public String getEmailExistedErr() {
        return emailExistedErr;
    }

    /**
     * @param emailExistedErr the emailExistedErr to set
     */
    public void setEmailExistedErr(String emailExistedErr) {
        this.emailExistedErr = emailExistedErr;
    }

    /**
     * @return the confirmNotMatched
     */
    public String getConfirmNotMatched() {
        return confirmNotMatched;
    }

    /**
     * @param confirmNotMatched the confirmNotMatched to set
     */
    public void setConfirmNotMatched(String confirmNotMatched) {
        this.confirmNotMatched = confirmNotMatched;
    }
    
}
