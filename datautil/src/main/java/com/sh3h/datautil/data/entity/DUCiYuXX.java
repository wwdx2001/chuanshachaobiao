package com.sh3h.datautil.data.entity;


public class DUCiYuXX implements IDUEntity {
    private int id;
    private int wordsid;
    private String wordscontent;
    private String wordsvalue;
    private String wordsremark;
    private int belongid;
    private int sortid;
    private int isactive;

    public DUCiYuXX() {
        this.id = 0;
        this.wordsid = 0;
        this.wordscontent = null;
        this.wordsvalue = null;
        this.wordsremark = null;
        this.belongid = 0;
        this.sortid = 0;
        this.isactive = 0;
    }

    public DUCiYuXX(int id,
                    int wordsid,
                    String wordscontent,
                    String wordsvalue,
                    String wordsremark,
                    int belongid,
                    int sortid,
                    int isactive) {
        this.id = id;
        this.wordsid = wordsid;
        this.wordscontent = wordscontent;
        this.wordsvalue = wordsvalue;
        this.wordsremark = wordsremark;
        this.belongid = belongid;
        this.sortid = sortid;
        this.isactive = isactive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordsid() {
        return wordsid;
    }

    public void setWordsid(int wordsid) {
        this.wordsid = wordsid;
    }

    public String getWordscontent() {
        return wordscontent;
    }

    public void setWordscontent(String wordscontent) {
        this.wordscontent = wordscontent;
    }

    public String getWordsvalue() {

        return wordsvalue;
    }

    public void setWordsvalue(String wordsvalue) {
        this.wordsvalue = wordsvalue;
    }
    public String getWordsremark() {
        return wordsremark;
    }

    public void setWordsremark(String wordsremark) {
        this.wordsremark = wordsremark;
    }

    public int getBelongid() {
        return belongid;
    }

    public void setBelongid(int belongid) {
        this.belongid = belongid;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    public String toString() {
        return this.wordscontent;
    }
}
