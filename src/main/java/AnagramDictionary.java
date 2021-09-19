import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * An {@code AnagramDictionary} identifies an instance to save
 * anagram words. An anagram of a word is another word with
 * exactly the same quantity of characters in it but in any order.
 * <p>
 * Underlying {@code HashMap<String, List<String>>} data structure
 * saves anagram words as a {@code List<String>} and uses a
 * {@code String} key to retrieve all anagrams in a constant time.
 */
public class AnagramDictionary {

    /**
     * {@code Map} instance used to save anagram words.
     */
    private final Map<String, List<String>> dictionary;

    /**
     * Constructs an empty {@code AnagramDictionary}.
     */
    public AnagramDictionary() {
        this.dictionary = new HashMap<>();
    }

    /**
     * Returns {@code List<String>} with all anagrams as a
     * {@code String} representation, separated by commas.
     *
     * @param anagrams {@code List<String>} to be represented
     *                 as a {@code String}.
     * @return {@code String} representation of {@code List<String>}
     * anagrams.
     */
    public static String getAnagramsAsString(List<String> anagrams) {
        if (anagrams.size() == 0) return "";

        StringBuilder result = new StringBuilder();
        anagrams.forEach(anagram -> result.append(anagram).append(","));
        return result.substring(0, result.length() - 1);
    }

    /**
     * Loads data into an {@code AnagramDictionary} instance. Prints
     * error message if data load is unsuccessful.
     *
     * @param pathToDictionary specifies {@code Path} to dictionary file.
     * @return {@code true} if data loaded successfully; {@code false} otherwise.
     */
    public boolean loadDictionary(Path pathToDictionary) {
        try (Stream<String> lines = Files.lines(pathToDictionary)) {

            lines.forEach(line -> {
                if (line.isEmpty()) return; //skip current iteration

                String key = getKey(line);
                if (!dictionary.containsKey(key)) {
                    dictionary.put(key, new ArrayList<>());
                }
                dictionary.get(key).add(line);
            });
            return true;

        } catch (NoSuchFileException e) {
            System.out.println("\nERROR: Dictionary not found at specified path");
        } catch (Exception e) {
            System.out.println("\nERROR: Unable to read dictionary file");
        }
        return false;
    }

    /**
     * Returns {@code List<String>} with all anagrams found in the
     * {@code AnagramDictionary} instance. If no anagrams found, returns
     * an empty {@code List<String>}.
     *
     * @param word which anagrams to be found for.
     * @return {@code List<String>} with found anagrams; empty
     * {@code List<String>} otherwise.
     */
    public List<String> getAnagrams(String word) {
        List<String> anagrams = new ArrayList<>();

        String key = getKey(word);
        if (dictionary.containsKey(key)) {
            anagrams = dictionary.get(key);
        }
        return anagrams;
    }

    /**
     * Normalizes passed {@code String} word to create a key for
     * underlying {@code HashMap<String, List<String>>} data structure.
     * For anagrams, it is all letters of the passed word sorted in
     * alphabetical order and normalized to lower case.
     *
     * @param word which anagrams to be found for.
     * @return normalized {@code String} key.
     */
    private String getKey(String word) {
        char[] letters = word.toLowerCase().toCharArray();
        Arrays.sort(letters);
        return Arrays.toString(letters);
    }

}
