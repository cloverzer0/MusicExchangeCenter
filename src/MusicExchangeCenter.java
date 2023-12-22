import java.util.*;

public class MusicExchangeCenter {
    private ArrayList<User> users;
    private HashMap<String ,Float> royalties;
    private List<Song> downloadedSongs;

    public MusicExchangeCenter(){
        users= new ArrayList<User>();
        royalties=new HashMap<String,Float>();
        downloadedSongs=new ArrayList<Song>();
    }
    public ArrayList<User> onlineUsers(){
        ArrayList<User> online=new ArrayList<User>();
        for (User u: users){
            if (u.isOnline()){
                online.add(u);
            }
        }
        return online;
    }
    public ArrayList<Song> allAvailableSongs(){
        ArrayList<Song> songs=new ArrayList<Song>();
        for (User u:onlineUsers()){
            for (Song s: u.getSongList()) {songs.add(s);}
        }
        return songs;
    }
    public String toString(){
        return "Music Exchange Center (" + onlineUsers().size() + " users on-line, " + allAvailableSongs().size() + " songs available)";
    }
    public User userWithName(String s){
        User check=null;
        for (User u: users){
            if (s.toLowerCase().equals(u.getUserName().toLowerCase())){
                check= u;
            }
        }
        return check;
    }
    public void registerUser(User x){
        if(userWithName(x.getUserName())==null){
            users.add(x);
        }
    }
    public ArrayList<Song> availableSongsByArtist(String artist){
        ArrayList<Song> songs=new ArrayList<Song>();
        for (Song s:allAvailableSongs()){
            if (s.getArtist().toLowerCase().equals(artist.toLowerCase())){
                songs.add(s);
            }
        }
        return songs;
    }
    public Song getSong(String title, String ownerName){
        Song song=null;
        for (User u:onlineUsers()){
            if (u.getUserName().equals(ownerName)){
                song=u.songWithTitle(title);
                if (song!=null){
                    downloadedSongs.add(song);
                    Float amount=0.25f;
                    if (royalties.get(song.getArtist())==null){
                        royalties.put(song.getArtist(),amount);
                    }else {
                        amount+=royalties.get(song.getArtist());
                        royalties.put(song.getArtist(),amount);
                    }
                }
            }
        }
        return song;
    }
    public void displayRoyalties(){
        if (downloadedSongs.size()>0){
            System.out.println(String.format("%s %s","Amount","Artist"));
            System.out.println("=============");
            for (String s: royalties.keySet()) {
               System.out.println(String.format("%.2f    %s",royalties.get(s),s));
            }
        }
    }
    public TreeSet<Song> uniqueDownloads(){
        TreeSet<Song> song=new TreeSet<Song>(downloadedSongs);
        return song;
    }

    public List<Song> getDownloadedSongs() {
        return downloadedSongs;
    }
    public ArrayList<Pair<Integer, Song>> songsByPopularity() {
        ArrayList<Pair<Integer, Song>> popularSongs = new ArrayList<Pair<Integer, Song>>();
        for (Song s : uniqueDownloads()) {
            int downloadCount = 0;
            for (Song downloadedSong : downloadedSongs) {
                if (s.equals(downloadedSong)) {
                    downloadCount++;
                }
            }
            if (!popularSongs.contains(new Pair<Integer, Song>(downloadCount, s))) {
                popularSongs.add(new Pair<Integer, Song>(downloadCount, s));
            }
        }
        Collections.sort(popularSongs, new Comparator<Pair<Integer, Song>>() {
            public int compare(Pair<Integer, Song> p1, Pair<Integer, Song> p2) {
                return p2.getKey()-p1.getKey();
            }
        });
        return popularSongs;
    }
}
