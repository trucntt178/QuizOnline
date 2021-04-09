/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblhistory;

import java.io.Serializable;


/**
 *
 * @author DELL
 */
public class TblHistoryDTO implements Serializable{
    private String subjectId;
    private String email;
    private String takeDate;
    private int totalQuestion;
    private int numOfCorrect;
    private float score;
    private boolean isDone;

    public TblHistoryDTO() {
    }

    public TblHistoryDTO(String subjectId, String email, String takeDate, int totalQuestion, int numOfCorrect, float score, boolean status) {
        this.subjectId = subjectId;
        this.email = email;
        this.takeDate = takeDate;
        this.totalQuestion = totalQuestion;
        this.numOfCorrect = numOfCorrect;
        this.score = score;
        this.isDone = status;
    }

    /**
     * @return the subjectId
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * @return the score
     */
    public float getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * @return the takeDate
     */
    public String getTakeDate() {
        return takeDate;
    }

    /**
     * @param takeDate the takeDate to set
     */
    public void setTakeDate(String takeDate) {
        this.takeDate = takeDate;
    }

    /**
     * @return the totalQuestion
     */
    public int getTotalQuestion() {
        return totalQuestion;
    }

    /**
     * @param totalQuestion the totalQuestion to set
     */
    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    /**
     * @return the numOfCorrect
     */
    public int getNumOfCorrect() {
        return numOfCorrect;
    }

    /**
     * @param numOfCorrect the numOfCorrect to set
     */
    public void setNumOfCorrect(int numOfCorrect) {
        this.numOfCorrect = numOfCorrect;
    }

    /**
     * @return the isDone
     */
    public boolean isIsDone() {
        return isDone;
    }

    /**
     * @param isDone the isDone to set
     */
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
    
}
