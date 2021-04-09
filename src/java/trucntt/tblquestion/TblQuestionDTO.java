/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblquestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import trucntt.tblanswer.TblAnswerDTO;
import trucntt.tblsubject.TblSubjectDTO;

/**
 *
 * @author DELL
 */
public class TblQuestionDTO implements Serializable{
    private int id;
    private TblSubjectDTO subject;
    private String content;
    private List<TblAnswerDTO> answerList;
    private Date createDate;
    private boolean status;

    public TblQuestionDTO() {
    }

    public TblQuestionDTO(int id, TblSubjectDTO subject, String content, List<TblAnswerDTO> answerList, Date createDate, boolean status) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.answerList = answerList;
        this.createDate = createDate;
        this.status = status;
    }
    
    public TblQuestionDTO(int id, String content, List<TblAnswerDTO> answerList) {
        this.id = id;
        this.content = content;
        this.answerList = answerList;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the answerList
     */
    public List<TblAnswerDTO> getAnswerList() {
        return answerList;
    }

    /**
     * @param answerList the answerList to set
     */
    public void setAnswerList(List<TblAnswerDTO> answerList) {
        this.answerList = answerList;
    }

    /**
     * @return the subject
     */
    public TblSubjectDTO getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(TblSubjectDTO subject) {
        this.subject = subject;
    }
    
            
}
