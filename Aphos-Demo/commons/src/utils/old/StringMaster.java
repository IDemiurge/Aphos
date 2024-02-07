package utils.old;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



public class StringMaster {

    public static final String standard_symbols = "'-(),";
    public static final String PREFIX_SEPARATOR = "::";
    public static final  String lineSeparator = "\n----------------------------\n";
    public static final  String lineSeparator2 = "\n--------------------------------------------------------\n";
    private static final String WHITESPACE_CODE = "%20";
    public static final String INDESTRUCTIBLE = " Indestructible";
    public static final String STRANGE_WALL = " Marked";
    static Pattern pattern_ = Pattern.compile("_");
    static Pattern pattern_space = Pattern.compile(" ");
    public static final String UPGRADE = format("UPGRADE");

    public static boolean compare(String string, String string2) {
        return compare(string, string2, false);
    }

    public static String capitalizeFirstLetter(String string) {
        char c = string.charAt(0);
        if (!Character.isAlphabetic(c)) {
            return string;
        }
        return Character.toUpperCase(c) + string.substring(1);

    }

    public static boolean contains(String string, String string2, boolean ignoreCase, boolean strict) {
        if (string.isEmpty()) {
            return false;
        }
        if (string2.isEmpty()) {
            return false;
        }
        if (ignoreCase) {
            string = string.toUpperCase();
            string2 = string2.toUpperCase();
        }
        if (string.contains(string2)) {
            return true;
        }
        if (strict) {
            return false;
        }
        if (format(string).contains(string2)) {
            return true;
        }
        if (string.contains(format(string2))) {
            return true;
        }
        return format(string).contains(format(string2));

    }

    public static boolean contains(String string, String string2) {
        return contains(string, string2, true, true);
    }

    public static boolean compareByChar(String string, String anotherString) {
        return compareByChar(string, anotherString, true);
    }

    public static boolean compareByChar(String string, String anotherString, boolean strict) {
        return compareByChar(string, anotherString, strict, null);
    }

    public static boolean compareByChar(String string, String anotherString, boolean strict,
                                        String... ignored) {
        // a non-wellformatted version of compare?

        // Math.max(v1.length, v2.length);
        if (anotherString == null) {
            return string == null;
        }
        if (string == null) {
            return false;
        }
        if (anotherString.isEmpty()) {
            return string.isEmpty();
        }
        if (string.isEmpty()) {
            return false;
        }

        if (anotherString.length() != string.length()) {
            //            if (strict) {
            //                return false;
            //            }
            // anotherString.replace("_", " "); costly!
            // string.replace("_", " ");

            if (anotherString.startsWith(" ")) {
                anotherString = anotherString.substring(1);
                return compareByChar(string, anotherString, false);
            }
            if (string.startsWith(" ")) {
                string = string.substring(1);
                return compareByChar(string, anotherString, false);
            }
            if (anotherString.endsWith(" ")) {
                anotherString = anotherString.substring(0, anotherString.length() - 1);
                return compareByChar(string, anotherString, false);

            }
            if (string.endsWith(" ")) {
                string = string.substring(0, string.length() - 1);
                return compareByChar(string, anotherString, false);
            }
            return false;
        }
        char[] v1 = string.toCharArray();
        char[] v2 = anotherString.toCharArray();
        int n = v1.length;

        int i = -1;
        int j = -1;
        while (n-- != 0) { // length?
            i++;
            j++;
            if (v1[i] != v2[j]) { // string cache?
                String char1 = "" + v1[i];
                String char2 = "" + v2[j];
                if (!char1.toLowerCase().equals(char2.toLowerCase())) {// to
                    // char
                    // comparison?
                    if (ignored != null) {
                        for (String c : ignored) {
                            if (c.equalsIgnoreCase("" + v1[i])) {
                                continue;
                            } else if (c.equalsIgnoreCase("" + v2[j])) {
                                continue;
                            }
                        }
                    }
                    if (v1[i] == '_') {
                        if (v2[j] == ' ') {
                            continue;
                        }
                    }
                    if (v1[i] == ' ') {
                        if (v2[j] == '_') {
                            continue;
                        }
                    }
                    // preCheck special chars
                    return false;
                }

            }
        }
        return true;

    }

    public static boolean compareContainers(String val1, String val2, boolean strictContents) {
        return compareContainers(val1, val2, strictContents, Strings.CONTAINER_SEPARATOR);
    }

    public static boolean compareContainersIdentical(String val1, String val2,
                                                     boolean strictContents, String delimiter) {
        List<String> container = ContainerUtils.openContainer(val1, delimiter);
        List<String> container2 = ContainerUtils.openContainer(val2, delimiter);

        if (container.size() != container2.size()) {
            return false;
        }

        for (String s1 : container) {
            boolean result = false;
            for (String s : container2) {
                if (StringMaster.compareByChar(s1, s, strictContents)) {
                    result = true;
                }
            }
            if (!result) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareContainers(String val1, String val2, boolean strictContents,
                                            String delimiter) {
        for (String s1 : ContainerUtils.open(val1, delimiter)) {
            for (String s : ContainerUtils.open(val2, delimiter)) {
                if (StringMaster.compareByChar(s1, s, strictContents)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean compare(String string, String string2, boolean strict) {
        if (isEmpty(string2)) {
            if (strict) {
                return isEmpty(string);
            }
            return true;
        }
        if (isEmpty(string)) {
            return isEmpty(string2);
        }

        boolean result = compareByChar(string, string2, strict);
        if (strict) {
            return result;
        } else if (result) {
            return true;
        } else {

            // if (string.contains(CONTAINER_SEPARATOR) || string2.contains(CONTAINER_SEPARATOR)) {
            //     if (compareContainers(string, string2, true, CONTAINER_SEPARATOR)) {
            //         return true;
            //     }
            // }
            // if (string.contains(VERTICAL_BAR) || string2.contains(VERTICAL_BAR)) {
            //     if (compareContainers(string, string2, true, VERTICAL_BAR)) {
            //         return true;
            //     }
            // }
            if (string.endsWith("s")) {
                if (removePlularEnding(string).equals(string2)) {
                    return true;
                }
            }
            if (string2.endsWith("s")) {
                if (removePlularEnding(string2).equals(string)) {
                    return true;
                }
            }

            if (contains(string, string2)) // TODO make it not strict if you
            // dare!
            {
                return true;
            }
            return contains(string2, string);
        }
    }

    public static String removePlularEnding(String textContent) {
        String str = "";
        int x = textContent.length();
        if (textContent.endsWith("s")) {
            str = textContent.substring(0, x - 1);
        }
        if (textContent.endsWith("ies")) {
            str = textContent.substring(0, x - 3);
            str += "y";
        }
        return str;
    }

    public static String toFormattedString(Object o) {
        if (o == null) {
            return "";
        }

        return format(o.toString());
    }

    public static String format(String s) {
        return StringMaster.format(s, false);
    }

    public static String getCamelCase(String name) {
        String formatted = StringMaster.format(name);
        return formatted.substring(0, 1).toLowerCase()
                + formatted.substring(1).replace(" ", "");
    }

    public static String format(String s, boolean insertSpaceAfterCapitals) {
        if (isEmpty(s)) {
            return "";
        }
        String string = null;

        if (s.contains(" ")) {  // TODO pattern_space.matcher("").matches()
            StringBuilder builder = new StringBuilder(s.length() + 5);
            s = s.trim();
            for (String str : pattern_space.split(s)) {
                builder.append(format(str)).append(" ");
            }
            string = builder.toString();
            string = string.substring(0, string.length() - 1);
            return string;
        }
        if (s.contains("_")) {
            StringBuilder builder = new StringBuilder(s.length() + 5);
            for (String str : pattern_.split(s)) {
                builder.append(format(str)).append(" ");
            }
            string = builder.toString();
            string = string.substring(0, string.length() - 1);
            return string;
        }


        if (insertSpaceAfterCapitals) {
            StringBuilder builder = new StringBuilder(s.length() + 5);

            for (char c : s.toCharArray()) {
                if (Character.isUpperCase(c)){
                    builder.append(" ");
                }
                builder.append(c);
            }
            string = builder.toString();
        } else {
            string = capitalizeFirstLetter(s.toLowerCase());
        }
        // }
        return string;
    }

    public static String getXMLTypeName(String typename) {
        return typename.replace(" ", "_");
    }

    public static String getFormattedEnumString(String name) {
        return format(name);
    }

    public static boolean isEmptyOrZero(String string) {
        if (string == null) {
            return true;
        }
        if (string.equals("0")) {
            return true;
        }
        return isEmpty(string);
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        return isEmpty(o.toString());
    }
    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        }
        string = string.trim();
        return string.isEmpty();
    }

    public static String getPercentageAppend(int amount) {
        // TODO Auto-generated method stub
        return "*(100+" + amount + ")/100";
    }

    public static String wrapInCurlyBraces(String arg) {
        return "{" + arg + "}";
    }

    public static String wrapInBrackets(String arg) {
        return "[" + arg + "]";
    }

    public static String wrapInParenthesis(String value) {
        return "(" + value + ")";
    }

    public static String removeParenthesis(String string) {
        return string.replace("(", "").replace(")", "");
    }

    public static String getObjTypeName(String name) {
        return format(name).toLowerCase();
    }

    public static String clipEnding(String str, String CHAR) {
        if (!str.contains(CHAR)) {
            return str;
        }
        return str.substring(0, str.indexOf(CHAR));
    }

    public static String getStringXTimes(int i, String s) {
        StringBuilder string = new StringBuilder();
        while (i > 0) {
            string.append(s);
            i--;
        }
        return string.toString();
    }

    public static String getWhiteSpaces(int i) {
        return getStringXTimes(i, " ");
    }

    public static int toInt(String string) {
        try {
            return Integer.valueOf(string);
        } catch (Exception e) {

        }
        return -1;
    }

    public static String toStringOrNull(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }


    public static String cropFormat(String str) {
        if (str.indexOf(".") < 1) {
            return str;
        }
        return str.substring(0, str.lastIndexOf("."));
    }

    public static String getFormat(String str) {
        if (str.indexOf(".") < 1) {
            return "";
        }
        return str.substring(str.lastIndexOf("."));
    }

    public static String getSubString(String string, String open, String close) {
        return getSubString(string, open, close, true);
        // try {
        // return string
        // .substring(string.indexOf(open), string.indexOf(close) + 1);
        // } catch (Exception e) {
        // return string;
        // }

    }

    public static String openNextParenthesis(String string) {
        // for each "(" inside the string, ignore the next ")"
        // String subString = getSubString(string, "(", ")", false);
        int indexOf = string.indexOf("(");
        if (indexOf == -1) {
            return string;
        }
        int endIndex = string.indexOf(")") + 1;
        if (endIndex == 0) {
            return string;
        }

        String subString = string.substring(indexOf, endIndex);

        while (getCount(subString, '(') > getCount(subString, ')')) { //
            endIndex = string.indexOf(")", endIndex) + 1;
            if (endIndex == 0) {
                break;
            }
            subString = string.substring(indexOf, endIndex);
        }
        return subString;

    }

    private static int getCount(String subString, char c) {
        int i = 0;
        for (char CHAR : subString.toCharArray()) {
            if (c == CHAR) {
                i++;
            }
        }
        return i;
    }

    public static String getSubString(boolean addTrailing, String string, String open,
                                      String close, Boolean inclusive) {
        int indexOf = string.indexOf(open);

        if (indexOf == -1) {
            return string;
        }
        if (inclusive == null) {
            indexOf = indexOf + open.length();
        } else if (!inclusive) {
            indexOf++;
        }
        int endIndex = string.indexOf(close);
        if (endIndex == -1) {
            return string;
        }
        if (inclusive != null) {
            if (inclusive) {
                endIndex++;
            }
        }

        if (addTrailing) {
            while (string.length() > endIndex) {
                if ((string.charAt(endIndex) + "").equals(close)) {
                    endIndex++;
                } else {
                    break;
                }
            }
        }

        return string.substring(indexOf, endIndex);
    }

    public static String getSubStringBetween(String string, String open, String close) {
        int indexOf = string.indexOf(open);

        if (indexOf == -1) {
            return string;
        }
        int endIndex = string.indexOf(close);
        if (endIndex == -1) {
            return string;
        }

        indexOf += open.length();

        return string.substring(indexOf, endIndex);
    }

    public static String getSubString(String string, char open, char close, boolean inclusive) {
        return getSubString(false, string, "" + open, "" + close, inclusive);

    }

    public static String getSubString(String string, String open, String close, boolean inclusive) {
        return getSubString(false, string, open, close, inclusive);

    }

    public static String cropParenthesises(String ref_substring) {
        return replaceLast(ref_substring.replaceFirst(Pattern.quote("("), ""), (")"), "");

    }

    public static boolean getBoolean(String value) {
        if (isEmpty(value)) {
            return false;
        }
        value = value.trim();
        if (value.equalsIgnoreCase("true")) {
            return true;
        }
        return value.equals("1");
    }

    public static String getLastPart(String string, String separator) {
        if (!string.contains(separator)) {
            return string;
        }
        LinkedList<String> segments = new LinkedList<>(Arrays.asList(string.split(separator)));
        if (segments.isEmpty()) {
            return string;
        }

        return segments.getLast();
    }

    public static String cropLastSegment(String path, String separator) {
        return cropLastSegment(path, separator, false);
    }

    public static String cropLastSegment(String path, String separator, boolean cropSeparator) {
        String s = replaceLast(path, getLastPart(path, separator), "");
        if (cropSeparator) {
            return s.substring(0, s.length() - 1);
        }
        return s;

    }

    public static String cropLast(String name, String string) {
        int lastIndexOf = name.lastIndexOf(string);
        if (lastIndexOf > -1) {
            return replaceLast(name, name.substring(lastIndexOf, lastIndexOf+string.length()), "");
        }
        return name;
    }
    public static String cropAfter(String name, String string) {
        int lastIndexOf = name.lastIndexOf(string);
        if (lastIndexOf > -1) {
            return replaceLast(name, name.substring(lastIndexOf), "");
        }
        return name;
    }

    public static String replaceLast(String string, String regex, String replacement) {
        return replace(string, regex, replacement, true);
    }

    public static String replaceFirst(String string, String regex, String replacement) {
        return replace(string, regex, replacement, false);
    }

    public static String replace(String string, String regex, String replacement, boolean last) {
        int index = (last) ? string.lastIndexOf(regex) : string.indexOf(regex);
        if (index == -1) {
            index = (last) ? string.lastIndexOf(regex) : string.indexOf(regex.toUpperCase());
        }
        if (index == -1) {
            index = (last) ? string.lastIndexOf(regex) : string.indexOf(regex.toLowerCase());
        }
        if (index == -1) {
            index = (last) ? string.lastIndexOf(regex) : string.indexOf(regex.trim());
        }
        if (index == -1) {
            index = (last) ? string.lastIndexOf(regex) : string
                    .indexOf(format(regex));
        }
        if (index == -1) {
            return string;
        }
        String prefix = string.substring(0, index);
        String suffix = string.substring(index + regex.length());
        return prefix + replacement + suffix;
    }

    public static String replace(boolean all, String string, String regex, String replacement) {
        if (all) {
            try {
                return string.replace(regex, replacement).replace(format(regex),
                        replacement).replace(regex.toLowerCase(), replacement).replace(
                        regex.toUpperCase(), replacement);
            } catch (Exception e) {
                return string.replace(Pattern.quote(regex), replacement);
            }
        } else {
            return replaceFirst(string, regex, replacement);
        }

    }

    public static String cropVersion(String string) {
        while (!isEmpty(string)) {
            char c = string.charAt(string.length() - 1);
            if (Character.isDigit(c) || c == 'v') {
                string = string.substring(0, string.length() - 1);
            } else {
                break;
            }
        }
        return string;
    }

    public static String cropFileVariant(String string) {
        while (!isEmpty(string)) {
            char c = string.charAt(string.length() - 1);
            if (Character.isDigit(c) || c == ' ' || c == '_' || c == '(' || c == ')') {
                string = string.substring(0, string.length() - 1);
            } else {
                break;
            }
        }

        return string;
    }


    public static String cropLast(String str1, int i, String suffix) {
        while (i > 0 && str1.endsWith(suffix)) {
            str1 = cropLast(str1, 1);
            i--;
        }
        return str1;
    }

    public static String cropFirstSegment(String str1, String separator) {
        return cropFirst(str1, str1.indexOf(separator) + 1);
    }

    public static String cropFirst(String str1, int i) {
        if (isEmpty(str1)) {
            return str1;
        }
        if (str1.length() < i) {
            return str1;
        }
        return str1.substring(i);
    }

    public static String cropLast(String str1, int i) {
        if (isEmpty(str1)) {
            return str1;
        }
        if (str1.length() < i) {
            return str1;
        }
        return str1.substring(0, str1.length() - i);
    }

    public static int compareSimilar(String firstString, String secondString) {
        int weight = 0;
        String longer = firstString.length() > secondString.length() ? firstString : secondString;
        String shorter = firstString.length() <= secondString.length() ? firstString : secondString;
        longer = longer.toLowerCase();
        shorter = shorter.toLowerCase();
        int i = 0;
        for (char c : longer.toCharArray()) {
            if (shorter.length() <= i) {
                break;
            }
            if (c == shorter.charAt(i)) {
                weight++;
            }
            i++;
        }
        weight -= longer.length() - shorter.length();
        return weight;
    }

    public static boolean checkSymbolsStandard(String newName) {
        for (char c : newName.toCharArray()) {
            if (!Character.isDigit(c)) {
                if (!Character.isAlphabetic(c)) {
                    if (c != ' ') {
                        if (!standard_symbols.contains(c + "")) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static Integer getWeight(String string) {
        return getWeight(string, false);
    }

    public static Integer getWeight(String string, boolean inverse) {
        if (inverse) {
            return NumberUtils.getInt(getWeightItem(string, false));
        }
        return NumberUtils.getInt(StringMaster.cropParenthesises(RandomWizard.getVarPartLast(string)));
    }

    public static String getWeightItem(String string, boolean inverse) {
        if (inverse) {
            return RandomWizard.getVarPartLast(string);
        }
        return string.replace(RandomWizard.getVarPartLast(string), "");
    }

    public static String getParamModString(String string, String mod) {
        return string + wrapInParenthesis(mod);
    }

    public static String getPossessive(String name) {
        if (name.endsWith("s")) {
            return name + "'";
        }
        return name + "'s";
    }

    public static String getLongestString(List<String> textLines) {
        int N = -1;
        String longest = null;
        for (String line : textLines) {
            int n = line.length();
            if (n > N) {
                N = n;
                longest = line;
            }
        }

        return longest;
    }

    public static String getModifierString(Integer mod) {
        String s = "";
        if (mod > 0) {
            s = "+";
        }
        s += mod + "%";
        return s;
    }

    public static String getBonusString(Integer bonus) {
        String s = "";
        if (bonus > 0) {
            s = "+";
        }
        s += bonus + "";
        return s;
    }

    public static String cropByLength(int maxLength, String string) {
        if (string.length() < maxLength) {
            return string;
        }
        return string.substring(0, maxLength);
    }

    public static Object getPrefix(String string) {
        String[] array = string.split(PREFIX_SEPARATOR);
        return array[0];
    }


    public static String getStringBeforeNumerals(String name) {
        int firstNumberIndex = NumberUtils.getFirstNumberIndex(name);
        if (firstNumberIndex == -1) {
            firstNumberIndex = name.length() - 1;
        }
        return name.substring(0, firstNumberIndex);
    }

    public static String getStringBeforeNumeralsAndSymbols(String name) {
        return name.substring(0, NumberUtils.getLastAlphabeticIndex(name));
    }

    public static String getFormattedTypeName(String typeName) {
        if (typeName.endsWith(";")) {
            typeName = typeName.substring(0, typeName.length() - 1);
        }

        return typeName;
    }

    public static String getFirstConsonants(String name, int n) {
        StringBuilder string = new StringBuilder();
        for (String sub : ContainerUtils.open(name, " ")) {
            string.append(sub.charAt(0));
        }
        return string.toString().toUpperCase();
    }

    // public static String getTypeNameFormat(String generic) {
    // //1.
    // cropFormat(str)
    // return null;
    // }

    public static String getAbbreviation(String name) {
        StringBuilder string = new StringBuilder();
        for (String sub : ContainerUtils.open(name, " ")) {
            string.append(sub.charAt(0));
        }
        return string.toString().toUpperCase();
    }

    public static String formatMapKey(String name) {
        return name.trim().toLowerCase();
    }


    public static String getFirstItem(String string, String separator) {
        if (isEmpty(string)) {
            return "";
        }
        return ContainerUtils.openContainer(string, separator).get(0);
    }

    public static String formatComparedProperty(String property) {
        return property.replace(";", "");
    }


    public static String wrap(String wrap, String enclosed) {
        return wrap + enclosed + wrap;
    }

    public static String tryGetSplit(String text, String separator, int i) {
        if (!text.contains(separator))
            return text;
        String[] array = text.split(separator);
        if (array.length <= i) {
            return "";
        }
        return array[i];
    }

    public static String getAppendedFile(String file, String suffix) {
        String format = getFormat(file);
        return cropFormat(file) + suffix + format;
    }


    public static String[] splitLines(String data) {
        return splitLines(data, false);
    }

    public static String[] splitLines(String data, boolean allowEmptyLines) {
        String[] lines = splitLines(data, allowEmptyLines, "\r?\n");
        if (lines.length > 1)
            return lines;
        return splitLines(data, allowEmptyLines, "\n");
    }

    public static String[] splitLines(String data, boolean allowEmptyLines, String separator) {
        if (allowEmptyLines)
            return data.trim().split(separator);
        //        data.contains(NEW_LINE)? StringMaster.NEW_LINE
        //       : "\n");
        List<String> list = Arrays.stream(data.trim().split(separator)).
                filter(line -> !line.isEmpty()).collect(Collectors.toList());
        return list.toArray(new String[0]);
    }

    public static String trimNewlines(String s) {
        while (s.startsWith(Strings.NEW_LINE))
            s = s.replaceFirst(Strings.NEW_LINE, "");
        return s;
    }

    public static boolean containsWord(String name, String word) {
        return name.equalsIgnoreCase(word) ||
                name.contains(" " + word) ||
                name.contains(word + " ");
    }

    public static String indent(int i) {
        return getStringXTimes(i, " ");
    }

    public static String removeNewLines(String text) {
        return ContainerUtils.construct("", StringMaster.splitLines(text));
    }

    public static String getBracketedPart(String typeName) {
        int index = typeName.indexOf("[");
        if (index == -1) {
            return "";
        }
        int index2 = typeName.indexOf("]");
        if (index2 == -1) {
            return "";
        }
        return typeName.substring(index, index2+1);
    }

    public static String getNameFromPath(String path) {
        return cropFormat(PathUtils.getLastPathSegment(path));
    }

    public static int countChar(String value, String r) {
        int i=0;
        for (char c : value.toCharArray()) {
            if (r.equalsIgnoreCase(""+c))
                i++;
        }
        return i;
    }

    public static String cropNumericSuffix(String path) {
        String suffix = NumberUtils.getNumericSuffix(cropFormat(path));
        return cropLast(path, suffix).trim()+getFormat(path);
    }

    public static String cropSuffix(String path, String cropSuffix) {
        return cropLast(path, cropSuffix).trim();
    }

    public static String fixWhiteSpaces(String s) {
        return s.replaceAll(WHITESPACE_CODE, " ");
    }

    public static int firstIndexOfAny(String s, String anyFrom) {
        for (char c : anyFrom.toCharArray()) {
            int i = s.indexOf("" + c);
            if (i>=0) {
                return i;
            }
        }
        return -1;
    }

}
