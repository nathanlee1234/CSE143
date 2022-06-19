// Nathan Lee
// TA: Yafqa Khan
// The Music Manager class manages a playlist and allows the 
// listener to get the next song from the playlist, preview 
// songs in the playlist, remove any song from the playlist, 
// and see all the songs they've removed from the playlist. 

import java.util.*;

public class MusicManager {
    
    // The list that represents the current playlist.
    private MusicNode playlist;

    // The list that represents the removed songs.
    private MusicNode removedList;

    // Prints the list of songs in the given current list.
    private void printSongs(MusicNode current) {
        while(current != null) {
            System.out.println("    " + current.song);
            current = current.next;
        }
    }

    // Returns true if the given song 
    // is in the given list of songs.
    private boolean findSong(MusicNode current, String song) {
        while(current != null) {
            if (song.equalsIgnoreCase(current.song)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Initializes a new music playlist with the given 
    // List of songs. Throws an IllegalArgumentException 
    // if the given list of songs is an empty list.
    public MusicManager(List<String> songs) {
        if (songs.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for(int i = songs.size() - 1; i >= 0; i--) {
            playlist = new MusicNode(songs.get(i), playlist);
        }
    }

    // Prints the songs in the current playlist, one  
    // per line with each line indented by 4 spaces.
    public void printCurrent() {
        MusicNode current = playlist;
        printSongs(current);
    }

    // Prints the removed songs, one per line 
    // with each line indented by 4 spaces.
    public void printRemoved() {
        MusicNode current = removedList;
        printSongs(current);
    }

    // Returns true if the given song is in the 
    // current playlist and false otherwise
    // ignoring case when comparing song names.
    public boolean currentContains(String song) {
        MusicNode current = playlist;
        return findSong(current, song);  
    }

    // Returns true if the given song is in the 
    // list of removed songs and false otherwise
    // ignoring case when comparing song names.
    public boolean removedContains(String song) {
        MusicNode current = removedList;
        return findSong(current, song);
    }

    // Returns true if there are 1 or more 
    // songs left in the current playlist.
    public boolean hasSongs() {
        if (playlist == null) {
            return false;
        }
        return (playlist != null);
    }

    // Returns the next song in the current playlist 
    // or returns null if there are no songs left.
    public String nextSong() {
        if (playlist == null) {
            return null;
        }
        return (playlist.song);
    }

    // Removes the given song, ignoring case, from the current 
    // playlist and transfers the given song to the front of 
    // the list of removed songs while maintaining the order
    // of the other songs in the current playlist. Throws 
    // an IllegalStateException if there are no songs left
    // to remove and throws and IllegalArgumentException 
    // if the song is not in the current playlist. If both
    // exceptions are true, IllegalStateException is thrown.
    public void remove(String song) {
        MusicNode removedTemp;
        if (playlist == null) {
            throw new IllegalStateException();
        } else if (song.equalsIgnoreCase(playlist.song)) {
            removedTemp = playlist;
            playlist = playlist.next;
        } else {
            MusicNode current = playlist; 
            while (!song.equalsIgnoreCase(current.next.song)) {
                current = current.next;
                if (current.next == null) {
                    throw new IllegalArgumentException();
                }
            }
            removedTemp = current.next;
            current.next = current.next.next;
        }  
        removedTemp.next = removedList;
        removedList = removedTemp; 
    }
}
