/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblquestion;

/**
 *
 * @author DELL
 */
public class TblQuestionCreateErrs {
    private String contentLengthErr;
    private String answer1LengthErr;
    private String answer2LengthErr;
    private String answer3LengthErr;
    private String answer4LengthErr;
    private String correctAnsErr;
    private String duplicatedAns;

    public TblQuestionCreateErrs() {
    }

    public TblQuestionCreateErrs(String contentLengthErr, String answer1LengthErr, String answer2LengthErr, String answer3LengthErr, String answer4LengthErr, String correctAnsErr, String duplicatedAns) {
        this.contentLengthErr = contentLengthErr;
        this.answer1LengthErr = answer1LengthErr;
        this.answer2LengthErr = answer2LengthErr;
        this.answer3LengthErr = answer3LengthErr;
        this.answer4LengthErr = answer4LengthErr;
        this.correctAnsErr = correctAnsErr;
        this.duplicatedAns = duplicatedAns;
    }

    /**
     * @return the contentLengthErr
     */
    public String getContentLengthErr() {
        return contentLengthErr;
    }

    /**
     * @param contentLengthErr the contentLengthErr to set
     */
    public void setContentLengthErr(String contentLengthErr) {
        this.contentLengthErr = contentLengthErr;
    }

    /**
     * @return the answer1LengthErr
     */
    public String getAnswer1LengthErr() {
        return answer1LengthErr;
    }

    /**
     * @param answer1LengthErr the answer1LengthErr to set
     */
    public void setAnswer1LengthErr(String answer1LengthErr) {
        this.answer1LengthErr = answer1LengthErr;
    }

    /**
     * @return the answer2LengthErr
     */
    public String getAnswer2LengthErr() {
        return answer2LengthErr;
    }

    /**
     * @param answer2LengthErr the answer2LengthErr to set
     */
    public void setAnswer2LengthErr(String answer2LengthErr) {
        this.answer2LengthErr = answer2LengthErr;
    }

    /**
     * @return the answer3LengthErr
     */
    public String getAnswer3LengthErr() {
        return answer3LengthErr;
    }

    /**
     * @param answer3LengthErr the answer3LengthErr to set
     */
    public void setAnswer3LengthErr(String answer3LengthErr) {
        this.answer3LengthErr = answer3LengthErr;
    }

    /**
     * @return the answer4LengthErr
     */
    public String getAnswer4LengthErr() {
        return answer4LengthErr;
    }

    /**
     * @param answer4LengthErr the answer4LengthErr to set
     */
    public void setAnswer4LengthErr(String answer4LengthErr) {
        this.answer4LengthErr = answer4LengthErr;
    }

    /**
     * @return the correctAnsErr
     */
    public String getCorrectAnsErr() {
        return correctAnsErr;
    }

    /**
     * @param correctAnsErr the correctAnsErr to set
     */
    public void setCorrectAnsErr(String correctAnsErr) {
        this.correctAnsErr = correctAnsErr;
    }

    /**
     * @return the duplicatedAns
     */
    public String getDuplicatedAns() {
        return duplicatedAns;
    }

    /**
     * @param duplicatedAns the duplicatedAns to set
     */
    public void setDuplicatedAns(String duplicatedAns) {
        this.duplicatedAns = duplicatedAns;
    }
    
}
