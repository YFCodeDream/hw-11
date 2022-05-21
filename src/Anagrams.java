import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author YFCodeDream
 * @version 1.0.0
 * @date 2022/5/14
 * @description Anagrams
 */
public class Anagrams {
    public static void main(String[] args) {
        System.out.println("Welcome to the anagram finder!\n");

        if (args.length != 1) {
            System.out.println("  usage: java Anagram some-text.txt\n");
            System.exit(0);
        }

        System.out.println("  reading file " + args[0] + "...");

        WordReader wr = new WordReader(args[0]);

        HashMap<String, LinkedList<String>> anagramsMap = getAnagramsMap(wr);
        printAnagramsMap(anagramsMap);
    }

    /**
     * 获取anagram的HashMap
     * @param wordReader 取单词类
     * @return anagram的HashMap
     */
    private static HashMap<String, LinkedList<String>> getAnagramsMap(WordReader wordReader) {
        HashMap<String, LinkedList<String>> anagramsMap = new HashMap<>();

        String currentWord;
        // 遍历wordReader
        while ((currentWord = wordReader.nextWord()) != null) {
            // 是否插入标志位
            AtomicReference<Boolean> hasInserted = new AtomicReference<>(false);
            // 复制一份currentWord，forEach遍历map的lambda表达式语法需求
            String finalCurrentWord = currentWord;
            
            // 遍历anagramsMap
            anagramsMap.forEach((keyWord, anagramsList) -> {
                // 如果currentWord与keyWord满足anagram
                if (checkKeyWordAnagram(finalCurrentWord, keyWord)) {
                    // 如果当前没有记录currentWord
                    if (!anagramsList.contains(finalCurrentWord)) {
                        // 加入对应列表，更改标志位
                        anagramsList.add(finalCurrentWord);
                        hasInserted.set(true);
                    }
                }
            });
            
            // 如果标志位是false，表示没有插入
            if (!hasInserted.get()) {
                // 没有插入有两种情况
                // 1. 与anagramsMap目前所有的键值都不是anagram
                // 2. 已经是anagramsMap的键值了
                // 所以，第二种情况不用管，考虑第一个情况，新建键值对记录这个单词，同时新建记录列表即可
                if (!anagramsMap.containsKey(currentWord)) {
                    anagramsMap.put(currentWord, new LinkedList<>());
                }
            }
        }

        return anagramsMap;
    }

    /**
     * 检查与当前的keyWord是不是互为anagram
     * @param currentWord 当前单词
     * @param keyWord 当前的keyWord
     * @return true if 互为anagram else false
     */
    private static boolean checkKeyWordAnagram(String currentWord, String keyWord) {
        return getWordCharFreqMap(currentWord).equals(getWordCharFreqMap(keyWord))
                && !currentWord.equals(keyWord);
    }

    /**
     * 获取一个单词字符频率
     * @param word 当前单词
     * @return 字符频率
     */
    private static HashMap<Character, Integer> getWordCharFreqMap(String word) {
        HashMap<Character, Integer> wordCharMap = new HashMap<>();
        for (Character currentWordChar : word.toCharArray()) {
            Integer currentWordCharFreq = wordCharMap.get(currentWordChar);
            currentWordCharFreq = currentWordCharFreq == null ? 0 : currentWordCharFreq;
            wordCharMap.put(currentWordChar, currentWordCharFreq + 1);
        }
        return wordCharMap;
    }

    /**
     * 打印anagram
     * @param anagramsMap anagram的HashMap
     */
    private static void printAnagramsMap(HashMap<String, LinkedList<String>> anagramsMap) {
        System.out.println("Found anagrams:");
        anagramsMap.forEach((keyWord, anagramsList) -> {
            StringBuilder resStr = new StringBuilder("-\t" + keyWord + "\t");
            if (!anagramsList.isEmpty()) {
                for (String anagram : anagramsList) {
                    resStr.append(anagram).append("\t");
                }
                System.out.println(resStr);
            }
        });
    }
}
