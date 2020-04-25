package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author i.isaev on 27.01.2020
 * @project unloading
 */
public class RepositoryData {

    private long id;
    private String author;
    private Timestamp commitDate;
    private String branch;
    private String changesetNumber;
    private String message;

    /*public RepositoryData() {
    }

    public RepositoryData(String author, Timestamp commitDate, String branch, String changesetNumber) {
        this.author = author;
        this.commitDate = commitDate;
        this.branch = branch;
        this.changesetNumber = changesetNumber;
    }*/

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Timestamp commitDate) {
        this.commitDate = commitDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getChangesetNumber() {
        return changesetNumber;
    }

    public void setChangesetNumber(String changesetNumber) {
        this.changesetNumber = changesetNumber;
    }

    @Override
    public String toString() {
        return "RepositoryData{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", commitDate=" + commitDate +
                ", branch='" + branch + '\'' +
                ", changesetNumber='" + changesetNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
