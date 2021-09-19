import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * An {@code AnagramFinder} is a CLI program that reads
 * a dictionary file and allows a user to find anagrams.
 * <p>
 * The program provides a command line prompt where a user
 * can input a word of their choice. On hitting enter the
 * program finds all anagrams, if any exist, of the word.
 * It prints them out on the next line as a comma separated
 * list.
 * <p>
 * If no anagrams are found it prints out "No anagrams
 * found for <word>".
 */
public class AnagramFinder {

    /**
     * The word that is being used as an exit command
     * from a command line prompt.
     */
    private static final String EXIT_COMMAND = ":exit";

    public static void main(String[] args) {

        System.out.println("Welcome to the Anagram Finder");
        System.out.println("---------------------------------");
        System.out.printf("Enter '%s' to exit the program\n\n", EXIT_COMMAND);

        if (args.length == 0) {
            System.out.println("No dictionary file name provided");
            return;
        }

        String fileName = args[0];
        String dir = System.getProperty("user.dir");
        Path path = Path.of(dir, fileName);

        AnagramDictionary dictionary = new AnagramDictionary();

        long start = System.nanoTime();
        boolean isLoaded = dictionary.loadDictionary(path);
        long end = System.nanoTime();

        if (!isLoaded) return;

        System.out.println(getMessageForLoadedDictionary(start, end));

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("\nAnagramFinder>");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase(EXIT_COMMAND)) break;

            start = System.nanoTime();
            List<String> anagrams = dictionary.getAnagrams(input);
            end = System.nanoTime();

            System.out.println(getMessageForFoundAnagrams(start, end, anagrams, input));
        }

        scanner.close();
    }

    /**
     * Returns {@code String} message with benchmark time required to
     * load data into the {@code AnagramDictionary}.
     *
     * @param start defines start time of data load.
     * @param end   defines finish time of data load.
     * @return {@code String} message with benchmark time calculated
     * in milliseconds.
     */
    private static String getMessageForLoadedDictionary(long start, long end) {
        long time = (end - start) / 1_000_000;
        return String.format("Dictionary loaded in %d ms", time);
    }

    /**
     * Returns {@code String} message with benchmark time required to
     * find all anagrams in the {@code AnagramDictionary}. Message
     * contains all anagrams as a comma separated list.
     *
     * @param start    defines start time of anagrams search.
     * @param end      defines finish time of anagrams search.
     * @param anagrams is a {@code List<String>} of all found anagrams.
     * @param word     is a {@code String} which anagrams have been found.
     * @return {@code String} message with benchmark time calculated
     * in milliseconds, and a comma separated list of found anagrams.
     */
    private static String getMessageForFoundAnagrams(long start, long end, List<String> anagrams, String word) {
        word = word.isEmpty() ? "empty input" : word;

        long time = (end - start) / 1_000_000;
        String message = String.format("No anagrams found for %s in %dms\n", word, time);

        if (anagrams.size() > 0) {
            int anagramsSize = anagrams.size();
            String plural = anagramsSize == 1 ? "" : "s";
            String anagramsString = AnagramDictionary.getAnagramsAsString(anagrams);
            message = String.format(
                    "%d Anagram%s found for %s in %dms\n%s\n",
                    anagramsSize, plural, word, time, anagramsString
            );
        }

        return message;
    }

}
