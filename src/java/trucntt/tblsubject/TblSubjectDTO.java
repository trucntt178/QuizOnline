/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblsubject;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class TblSubjectDTO implements Serializable{
    private String subjectId;
    private String name;
    private int time;
    private int numOfQuestion;

    public TblSubjectDTO() {
    }

    public TblSubjectDTO(String subjectId, String name) {
        this.subjectId = subjectId;
        this.name = name;
    }

    public TblSubjectDTO(String subjectId, String name, int time, int numOfQuestion) {
        this.subjectId = subjectId;
        this.name = name;
        this.time = time;
        this.numOfQuestion = numOfQuestion;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the numOfQuestion
     */
    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    /**
     * @param numOfQuestion the numOfQuestion to set
     */
    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }

    
}
