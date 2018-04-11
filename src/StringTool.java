/**
 * Created by Sam Baek on 3/9/2017.
 */
public class StringTool {
    public final static String DOUBLE_BAR = "||";
    public final static String COMMA_DELIMITER = ",";
    public final static String QUOTE = "\"";
    public final static String NEW_LINE_SEPARATOR = "\n";
    public final static String FILE_PDF = ".pdf";

    private final static String[] CAPITALIZE_WORDS = {
            "GA", "I", "II", "III", "IV", "V", "LLC", "LLC."
    };

    public static String capitalizeWords(String line) {
        StringBuilder builder = new StringBuilder();
        for (String str : line.split(" ")) {
            builder.append(capitalizeWord(str) + " ");
        }
        return builder.toString().trim();
    }

    private static String capitalizeWord(String str) {
        for (String s : CAPITALIZE_WORDS) {
            if (s.equalsIgnoreCase(str)) {
                return s;
            }
        }
        if (str.length() > 0) {
            if (str.length() > 1 && Character.isUpperCase(str.charAt(1))) {
                str = str.toLowerCase();
            }
            char[] arr = str.toCharArray();
            arr[0] = Character.toUpperCase(arr[0]);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static int techDistance(String str) {
        String[] TECH_SPELLINGS = {
//                "GEORGIA, TECH RES INST",
                "GEORGIA TECH RESEARCH INSTITUTE",
                "GEORGIA TECH",
                "GEORGIA TECH RESEARCH CORPORATION",
                "GEORGIA TECHNOLOGY RESEARCH CORPORATION",
                "GEORGIA INSTITUTE OF TECHNOLOGY"};
        int min = Integer.MAX_VALUE;
        for (String tech : TECH_SPELLINGS) {
            int comp = distance(str, tech);
            min = comp < min ? comp : min;
        }
        return min;
    }

    // Levenshtein Distance
    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}
