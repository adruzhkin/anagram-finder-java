import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

@DisplayName("Anagram Dictionary Tests")
public class AnagramDictionaryTests {

    private static AnagramDictionary dictionary;

    @BeforeAll
    public static void create_This_Dictionary() {
        dictionary = new AnagramDictionary();
        String fileName = "dictionary.txt";
        String dir = System.getProperty("user.dir");
        Path path = Path.of(dir, fileName);
        dictionary.loadDictionary(path);
    }

    @Test
    @DisplayName("Load dictionary when file exist")
    public void loadDictionary_True() {
        String fileName = "dictionary.txt";
        String dir = System.getProperty("user.dir");
        Path path = Path.of(dir, fileName);
        AnagramDictionary dictionary = new AnagramDictionary();
        Assertions.assertTrue(dictionary.loadDictionary(path));
    }

    @Test
    @DisplayName("Do not load dictionary when file does not exist")
    public void loadDictionary_False() {
        String fileName = "dictionary";
        String dir = System.getProperty("user.dir");
        Path path = Path.of(dir, fileName);
        AnagramDictionary dictionary = new AnagramDictionary();
        Assertions.assertFalse(dictionary.loadDictionary(path));
    }

    @Test
    @DisplayName("Get valid anagrams for existing word in lower case")
    public void getAnagrams_Existing_Word_Lower_Case_True() {
        List<String> anagrams = dictionary.getAnagrams("stop");
        Assertions.assertEquals(4, anagrams.size());
        Assertions.assertEquals("post", anagrams.get(0));
        Assertions.assertEquals("spot", anagrams.get(1));
        Assertions.assertEquals("stop", anagrams.get(2));
        Assertions.assertEquals("tops", anagrams.get(3));
    }

    @Test
    @DisplayName("Get valid anagrams for existing word in upper case")
    public void getAnagrams_Existing_Word_Upper_Case_True() {
        List<String> anagrams = dictionary.getAnagrams("STOP");
        Assertions.assertEquals(4, anagrams.size());
        Assertions.assertEquals("post", anagrams.get(0));
        Assertions.assertEquals("spot", anagrams.get(1));
        Assertions.assertEquals("stop", anagrams.get(2));
        Assertions.assertEquals("tops", anagrams.get(3));
    }

    @Test
    @DisplayName("Get empty anagrams for not existing word")
    public void getAnagrams_Not_Existing_Word_True() {
        List<String> anagrams = dictionary.getAnagrams("abracadabraa");
        Assertions.assertEquals(0, anagrams.size());
    }

    @Test
    @DisplayName("Get valid anagrams for word 'exit'")
    public void getAnagrams_Word_Exit_True() {
        List<String> anagrams = dictionary.getAnagrams("exit");
        Assertions.assertEquals(1, anagrams.size());
        Assertions.assertEquals("exit", anagrams.get(0));
    }

    @Test
    @DisplayName("Get empty string as anagram representation for empty anagrams")
    public void getAnagramAsString_Empty_Anagrams_True() {
        List<String> anagrams = dictionary.getAnagrams("");
        String anagramsAsString = AnagramDictionary.getAnagramsAsString(anagrams);
        Assertions.assertEquals("", anagramsAsString);
    }

    @Test
    @DisplayName("Get valid string as anagram representation for one anagram")
    public void getAnagramAsString_One_Anagram_True() {
        List<String> anagrams = dictionary.getAnagrams("exit");
        String anagramsAsString = AnagramDictionary.getAnagramsAsString(anagrams);
        Assertions.assertEquals("exit", anagramsAsString);
    }

    @Test
    @DisplayName("Get valid string as anagram representation for multiple anagrams")
    public void getAnagramAsString_Multiple_Anagrams_True() {
        List<String> anagrams = dictionary.getAnagrams("stop");
        String anagramsAsString = AnagramDictionary.getAnagramsAsString(anagrams);
        Assertions.assertEquals("post,spot,stop,tops", anagramsAsString);
    }

}
