// Class MusicPlaylistMain is the driver program for a looping music playlist.
// It reads song titles from a file, shuffles them if the user so desires, and
// uses them to simulate music playback. The user is asked for the name of a
// song to remove from the playlist (defaults to the next song).

import java.io.*;
import java.util.*;

public class MusicMain {
    public static void main(String[] args) throws FileNotFoundException {
        // prompt for file name
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the cse143 music playlist");
        System.out.println();
        System.out.print("Whose playlist do you want to use this time? ");
        String fileName = console.nextLine();

        // read songs into a list, using a Set to avoid duplicates
        Scanner input = new Scanner(new File(fileName));
        Set<String> songs = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        List<String> songs2 = new ArrayList<>();
        while (input.hasNextLine()) {
            String song = input.nextLine().trim();
            if (song.length() > 0 && !songs.contains(song)) {
                songs.add(song);
                songs2.add(song);
            }
        }

        // shuffle if desired
        if (yesTo(console, "Do you want to shuffle the playlist?")) {
            Collections.shuffle(songs2);
        }
        // make an immutable version and use it to build a MusicManager
        List<String> songs3 = Collections.unmodifiableList(songs2);
        MusicManager manager = new MusicManager(songs3);
        System.out.println();

        // prompt the user for songs to play or skip until the playlist is over
        while (manager.hasSongs()) {
            System.out.println("Current songs:");
            manager.printCurrent();
            System.out.println("Removed songs:");
            manager.printRemoved();
            String next = manager.nextSong();
            System.out.println("Now playing... " + next);
            System.out.println();
            System.out.print("Remove a song? ");
            String song = console.nextLine().trim();
            if (song.isBlank()) {
                song = next;
            }
            if (manager.removedContains(song)) {
                System.out.println(song + " is already removed.");
            } else if (!manager.currentContains(song)) {
                System.out.println("Unknown song.");
            } else {
                manager.remove(song);
            }
            System.out.println();
        }
        System.out.println("Empty playlist.");
    }

    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public static boolean yesTo(Scanner console, String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
