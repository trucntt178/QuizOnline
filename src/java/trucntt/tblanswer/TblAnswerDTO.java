/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblanswer;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class TblAnswerDTO implements Serializable{
    private int id;
    private String content;
    private boolean isTrue;

    public TblAnswerDTO() {
    }

    public TblAnswerDTO(int id, String content, boolean isTrue) {
        this.id = id;
        this.content = content;
        this.isTrue = isTrue;
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
     * @return the isTrue
     */
    public boolean isIsTrue() {
        return isTrue;
    }

    /**
     * @param isTrue the isTrue to set
     */
    public void setIsTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }
    
}
