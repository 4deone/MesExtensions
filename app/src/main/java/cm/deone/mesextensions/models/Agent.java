package cm.deone.mesextensions.models;

public class Agent {

    private String addedby;
    private String campagne;
    private String domain;
    private long extension;
    private String login;
    private String nom;
    private String password;
    private String updatedby;
    private String web;

    public Agent() {
    }

    public Agent(String addedby, String campagne, String domain, long extension, String login,
                 String nom, String password, String updatedby, String web) {
        this.addedby = addedby;
        this.campagne = campagne;
        this.domain = domain;
        this.extension = extension;
        this.login = login;
        this.nom = nom;
        this.password = password;
        this.updatedby = updatedby;
        this.web = web;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getExtension() {
        return extension;
    }

    public void setExtension(long extension) {
        this.extension = extension;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String name) {
        this.nom = name;
    }

    public String getCampagne() {
        return campagne;
    }

    public void setCampagne(String campagne) {
        this.campagne = campagne;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }
}
