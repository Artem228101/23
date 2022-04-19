package sample.database;

public class RequestsData {
    private int id;
    private int idFilm;
    private String username;

    public RequestsData(int id, int idFilm, String username) {
        this.id = id;
        this.idFilm = idFilm;
        this.username = username;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getIdFilm() {return idFilm;}
    public void setIdFim(int idFilm) {this.idFilm = idFilm;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
}
