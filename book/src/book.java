import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class book {
    public static void main(String[] args) throws IOException {

        System.out.println(fileCommonWord("raamat.txt"));
        System.out.println(fileLongestWord("raamat.txt"));
    }

    /**
     *
     * Find the longest word in the text. If more than one word qualifies, find them all.
     * @param fileName File name where to find longest word
     *
     * @return List of longest words
     *
     * @throws FileNotFoundException when file is not found.
     */
    private static List fileLongestWord(String fileName) throws FileNotFoundException {
        File file = new File(fileName);

        Scanner scan = new Scanner(file);
        HashMap<String, Integer> longestWord = new HashMap<>();
        List<String> listLongestWord = new ArrayList<>();

        while (scan.hasNextLine()) {
            Scanner s2 = new Scanner(scan.nextLine());
            // Regular expression to get rif of other characters.
            s2.useDelimiter("[^A-Za-z]+");

            while (s2.hasNext()) {
                String s = s2.next().toLowerCase();
                if (s.length() >= 8) {
                    longestWord.put(s, s.length());
                }
            }
        }

        // Sorting HashMap by Values.
        // Convert the entries into a stream, and use the comparator combinators from Map.Entry.
        Map<String, Integer> longestWord1 = longestWord.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Loops through longest word HashMap and puts longest words to new Array list.
        Object firstKey = longestWord1.values().toArray()[0];
        for (String key : longestWord1.keySet()) {
            if (key.length() == (int) firstKey) {
                listLongestWord.add(key);
            }
        }

        return listLongestWord;
    }

    /**
     *
     * Find the most common word in the text with 8 or more characters.
     *
     * @param fileName File name where to find the common word.
     *
     * @return map of common word.
     *
     * @throws FileNotFoundException when file is not found.
     */
    private static Map fileCommonWord(String fileName) throws FileNotFoundException {
        File file = new File(fileName);

        Scanner scan = new Scanner(file);

        HashMap<String, Integer> commonWord = new HashMap<>();

        // word quantity after first time added to HashMap.
        int quantity = 1;
        while (scan.hasNextLine()) {
            Scanner s2 = new Scanner(scan.nextLine());
            // Regular expression to get rif of other characters.
            s2.useDelimiter("[^A-Za-z]+");

            while (s2.hasNext()) {
                String s = s2.next().toLowerCase();
                if (s.length() >= 8) {

                    if (commonWord.containsKey(s)) {
                        for (String key : commonWord.keySet()) {
                            if (key.equals(s)) {
                                // If word is in HashMap then the value is increased.
                                commonWord.put(s, commonWord.get(key) + 1);
                            }
                        }
                    } else {
                        commonWord.put(s, quantity);
                    }
                }
            }
        }

        // Sorting HashMap by Values, limited by 1.
        // Convert the entries into a stream, and use the comparator combinators from Map.Entry.

        return commonWord.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
