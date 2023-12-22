public class Song implements Comparable<Song>{
  private String title;
  private String artist;
  private User owner;
  private int duration;
  
  public Song()  {
    this("", "", 0, 0);
  }
  
  public Song(String t, String a, int m, int s)  {
    title = t;
    artist = a;
    duration = m * 60 + s;
    owner =null;
  }
  
  public String getTitle() { 
    return title; 
  } 
  
  public String getArtist() { 
    return artist; 
  }
  
  public int getDuration() { 
    return duration; 
  }
  
  public int getMinutes() {
    return duration / 60;
  }
  
  public int getSeconds() {
    return duration % 60;
  }
  
  public String toString()  {
    return("\"" + title + "\" by " + artist + " " + (duration / 60) + ":" + (duration%60));
  }
  public void setOwner(User u){
    owner = u;
  }
  public User getOwner() {
    return owner;
  }
  public int compareTo(Song s) {
    return (this.title.compareTo(s.title));
  }
  public boolean equals(Object x) {
    if (!(x instanceof Song)){
      return false;
    }
    return (title.equals(((Song)x).title))&(artist.equals(((Song)x).artist))&(owner.equals(((Song)x).owner));
  }
  public int hashCode() { return (title.hashCode()+artist.hashCode()+owner.hashCode()+duration);
  }
}
