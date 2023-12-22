import java.util.ArrayList;
import java.util.List;

public class User {
  private String     userName;
  private boolean    online;
  private ArrayList<Song> songList;
  
  public User()  { this(""); }
  
  public User(String u)  {
    userName = u;
    online = false;
    songList= new ArrayList<Song>();
  }
  public void addSong(Song s){
    songList.add(s);
    s.setOwner(this);
  }
  public int totalSongTime(){
    int sum=0;
    for (Song s: songList){
      sum+=s.getSeconds();
    }
    return sum;
  }
  
  public String getUserName() { return userName; }
  public boolean isOnline() { return online; }
  public ArrayList<Song> getSongList() { return songList;}
  
  public String toString()  {
    String s = "" + userName + ": " + songList.size() + " songs (";
    if (!online) s += "not ";
    return s + "online)";
  }
  public void register(MusicExchangeCenter m){
    if (m.userWithName(userName) == null) {
      m.registerUser(this);
    }
  }
  public void logon(){
    online=true;
  }
  public void logoff(){
    online=false;
  }
  public List<String> requestCompleteSonglist(MusicExchangeCenter m){
    List<String> ListFormat=new ArrayList<String>();
    int count=1;
    System.out.println(String.format("%11S %36S %19S %8S","title","artist","time","owner"));
      for (Song s:m.allAvailableSongs()) {
        if(s.getSeconds()>=10) {
          ListFormat.add(String.format("%2d. %-35s %-15s %7d:%d    %s", count, s.getTitle(), s.getArtist(), s.getMinutes(), s.getSeconds(), s.getOwner().userName));
        }
        else {
          ListFormat.add(String.format("%2d. %-35s %-15s %7d:0%d    %s", count, s.getTitle(), s.getArtist(), s.getMinutes(), s.getSeconds(), s.getOwner().userName));
        }
        count++;
      }
    return ListFormat;
  }
  public List<String> requestSonglistByArtist(MusicExchangeCenter m, String artist){
    List<String> ListFormat=new ArrayList<String>();
    int count=1;
    System.out.println(String.format("%11S %36S %19S %8S","title","artist","time","owner"));
    for (Song s:m.availableSongsByArtist(artist)) {
      ListFormat.add(String.format("%2d. %-35s %-15s %7d:%d    %s",count,s.getTitle(),s.getArtist(),s.getMinutes(),s.getSeconds(),s.getOwner().userName));
      count++;
    }
    return ListFormat;
  }
  public Song songWithTitle(String title){
    Song song=null;
    for (Song s: songList){
      if (s.getTitle().equals(title)){
        song=s;
      }
    }
    return song;
  }
  public void downloadSong(MusicExchangeCenter m, String title, String ownerName){
    Song downloadedSong= m.getSong(title,ownerName);
    if (downloadedSong!=null){
      songList.add(downloadedSong);
    }
  }
}
