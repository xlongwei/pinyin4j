/**
 * This file is part of pinyin4j (http://sourceforge.net/projects/pinyin4j/) and distributed under
 * GNU GENERAL PUBLIC LICENSE (GPL).
 * <p>
 * pinyin4j is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * <p>
 * pinyin4j is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with pinyin4j.
 */

package net.sourceforge.pinyin4j;

import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import net.sourceforge.pinyin4j.multipinyin.Trie;

/**
 * A class provides several utility functions to convert Chinese characters
 * (both Simplified and Tranditional) into various Chinese Romanization
 * representations
 *
 * @author Li Min (xmlerlimin@gmail.com)
 */
public class PinyinHelper {

    private static final String[] ARR_EMPTY = {};

    /**
     * Get all unformmatted Hanyu Pinyin presentations of a single Chinese
     * character (both Simplified and Tranditional)
     * <p>
     * <p>
     * For example, <br/> If the input is '间', the return will be an array with
     * two Hanyu Pinyin strings: <br/> "jian1" <br/> "jian4" <br/> <br/> If the
     * input is '李', the return will be an array with single Hanyu Pinyin
     * string: <br/> "li3"
     * <p>
     * <p>
     * <b>Special Note</b>: If the return is "none0", that means the input
     * Chinese character exists in Unicode CJK talbe, however, it has no
     * pronounciation in Chinese
     *
     * @param ch the given Chinese character
     * @return a String array contains all unformmatted Hanyu Pinyin
     * presentations with tone numbers; null for non-Chinese character
     */
    static public String[] toHanyuPinyinStringArray(int ch) {
        return getUnformattedHanyuPinyinStringArray(ch);
    }

    /**
     * Get all Hanyu Pinyin presentations of a single Chinese character (both
     * Simplified and Tranditional)
     * <p>
     * <p>
     * For example, <br/> If the input is '间', the return will be an array with
     * two Hanyu Pinyin strings: <br/> "jian1" <br/> "jian4" <br/> <br/> If the
     * input is '李', the return will be an array with single Hanyu Pinyin
     * string: <br/> "li3"
     * <p>
     * <p>
     * <b>Special Note</b>: If the return is "none0", that means the input
     * Chinese character is in Unicode CJK talbe, however, it has no
     * pronounciation in Chinese
     *
     * @param ch           the given Chinese character
     * @param outputFormat describes the desired format of returned Hanyu Pinyin String
     * @return a String array contains all Hanyu Pinyin presentations with tone
     * numbers; return empty string for non-Chinese character
     * @throws BadHanyuPinyinOutputFormatCombination if certain combination of output formats happens
     * @see HanyuPinyinOutputFormat
     * @see BadHanyuPinyinOutputFormatCombination
     */
    static public String[] toHanyuPinyinStringArray(int ch, HanyuPinyinOutputFormat outputFormat)
            throws BadHanyuPinyinOutputFormatCombination {
        return getFormattedHanyuPinyinStringArray(ch, outputFormat);
    }

    /**
     * Return the formatted Hanyu Pinyin representations of the given Chinese
     * character (both in Simplified and Tranditional) in array format.
     *
     * @param ch           the given Chinese character
     * @param outputFormat Describes the desired format of returned Hanyu Pinyin string
     * @return The formatted Hanyu Pinyin representations of the given codepoint
     * in array format; null if no record is found in the hashtable.
     */
    static private String[] getFormattedHanyuPinyinStringArray(int ch,
                                                               HanyuPinyinOutputFormat outputFormat) throws BadHanyuPinyinOutputFormatCombination {
        String[] pinyinStrArray = getUnformattedHanyuPinyinStringArray(ch);

        if (null != pinyinStrArray) {

            for (int i = 0; i < pinyinStrArray.length; i++) {
                pinyinStrArray[i] = PinyinFormatter.formatHanyuPinyin(pinyinStrArray[i], outputFormat);
            }

            return pinyinStrArray;

        } else
            return ARR_EMPTY;
    }

    /**
     * Delegate function
     *
     * @param ch the given Chinese character
     * @return unformatted Hanyu Pinyin strings; null if the record is not found
     */
    private static String[] getUnformattedHanyuPinyinStringArray(int ch) {
        return ChineseToPinyinResource.getInstance().getHanyuPinyinStringArray(ch);
    }

    /**
     * Get all unformmatted Tongyong Pinyin presentations of a single Chinese
     * character (both Simplified and Tranditional)
     *
     * @param ch the given Chinese character
     * @return a String array contains all unformmatted Tongyong Pinyin
     * presentations with tone numbers; null for non-Chinese character
     * @see #toHanyuPinyinStringArray(char)
     */
    static public String[] toTongyongPinyinStringArray(char ch) {
        return convertToTargetPinyinStringArray(ch, PinyinRomanizationType.TONGYONG_PINYIN);
    }

    /**
     * Get all unformmatted Wade-Giles presentations of a single Chinese
     * character (both Simplified and Tranditional)
     *
     * @param ch the given Chinese character
     * @return a String array contains all unformmatted Wade-Giles presentations
     * with tone numbers; null for non-Chinese character
     * @see #toHanyuPinyinStringArray(char)
     */
    static public String[] toWadeGilesPinyinStringArray(char ch) {
        return convertToTargetPinyinStringArray(ch, PinyinRomanizationType.WADEGILES_PINYIN);
    }

    /**
     * Get all unformmatted MPS2 (Mandarin Phonetic Symbols 2) presentations of
     * a single Chinese character (both Simplified and Tranditional)
     *
     * @param ch the given Chinese character
     * @return a String array contains all unformmatted MPS2 (Mandarin Phonetic
     * Symbols 2) presentations with tone numbers; null for non-Chinese
     * character
     * @see #toHanyuPinyinStringArray(char)
     */
    static public String[] toMPS2PinyinStringArray(char ch) {
        return convertToTargetPinyinStringArray(ch, PinyinRomanizationType.MPS2_PINYIN);
    }

    /**
     * Get all unformmatted Yale Pinyin presentations of a single Chinese
     * character (both Simplified and Tranditional)
     *
     * @param ch the given Chinese character
     * @return a String array contains all unformmatted Yale Pinyin
     * presentations with tone numbers; null for non-Chinese character
     * @see #toHanyuPinyinStringArray(char)
     */
    static public String[] toYalePinyinStringArray(char ch) {
        return convertToTargetPinyinStringArray(ch, PinyinRomanizationType.YALE_PINYIN);
    }

    /**
     * @param ch                 the given Chinese character
     * @param targetPinyinSystem indicates target Chinese Romanization system should be
     *                           converted to
     * @return string representations of target Chinese Romanization system
     * corresponding to the given Chinese character in array format;
     * null if error happens
     * @see PinyinRomanizationType
     */
    private static String[] convertToTargetPinyinStringArray(char ch,
                                                             PinyinRomanizationType targetPinyinSystem) {
        String[] hanyuPinyinStringArray = getUnformattedHanyuPinyinStringArray(ch);

        if (null != hanyuPinyinStringArray) {
            String[] targetPinyinStringArray = new String[hanyuPinyinStringArray.length];

            for (int i = 0; i < hanyuPinyinStringArray.length; i++) {
                targetPinyinStringArray[i] =
                        PinyinRomanizationTranslator.convertRomanizationSystem(hanyuPinyinStringArray[i],
                                PinyinRomanizationType.HANYU_PINYIN, targetPinyinSystem);
            }

            return targetPinyinStringArray;

        } else
            return ARR_EMPTY;
    }

    /**
     * Get all unformmatted Gwoyeu Romatzyh presentations of a single Chinese
     * character (both Simplified and Tranditional)
     *
     * @param ch the given Chinese character
     * @return a String array contains all unformmatted Gwoyeu Romatzyh
     * presentations with tone numbers; null for non-Chinese character
     * @see #toHanyuPinyinStringArray(char)
     */
    static public String[] toGwoyeuRomatzyhStringArray(char ch) {
        return convertToGwoyeuRomatzyhStringArray(ch);
    }

    /**
     * @param ch the given Chinese character
     * @return Gwoyeu Romatzyh string representations corresponding to the given
     * Chinese character in array format; null if error happens
     * @see PinyinRomanizationType
     */
    private static String[] convertToGwoyeuRomatzyhStringArray(char ch) {
        String[] hanyuPinyinStringArray = getUnformattedHanyuPinyinStringArray(ch);

        if (null != hanyuPinyinStringArray) {
            String[] targetPinyinStringArray = new String[hanyuPinyinStringArray.length];

            for (int i = 0; i < hanyuPinyinStringArray.length; i++) {
                targetPinyinStringArray[i] =
                        GwoyeuRomatzyhTranslator.convertHanyuPinyinToGwoyeuRomatzyh(hanyuPinyinStringArray[i]);
            }

            return targetPinyinStringArray;

        } else
            return ARR_EMPTY;
    }

    static public String[] toHanYuPinyinStringArray(String str, HanyuPinyinOutputFormat outputFormat) throws BadHanyuPinyinOutputFormatCombination {
        ChineseToPinyinResource resource = ChineseToPinyinResource.getInstance();

        int[] codePoints = toCodePointArray(str);
        String[] array = new String[codePoints.length];

        for (int i = 0; i < codePoints.length; i++) {
            String[] pinyinStrArray = null;//匹配到的最长的结果
            int codePoint = codePoints[i];
            Trie currentTrie = resource.getUnicodeToHanyuPinyinTable();
            int success = i;
            int current = i;
            do {
                String hexStr = hexStr(codePoint);
                currentTrie = currentTrie.get(hexStr);
                if (currentTrie != null) {
                    if (currentTrie.getPinyin() != null) {
                        pinyinStrArray = currentTrie.getPinyinArray();
                        success = current;
                    }
                    currentTrie = currentTrie.getNextTire();
                }
                current++;
                if (current < codePoints.length)
                    codePoint = codePoints[current];
                else
                    break;
            }
            while (currentTrie != null);

            if (pinyinStrArray == null) {//如果在前缀树中没有匹配到，那么它就不能转换为拼音，直接输出或者去掉
                array[i] = new StringBuilder().appendCodePoint(codePoints[i]).toString();
            } else {
                if (pinyinStrArray != null) {
                    for (int j = 0; j < pinyinStrArray.length; j++) {
                        array[i + j] = (outputFormat==null ? pinyinStrArray[j] : PinyinFormatter.formatHanyuPinyin(pinyinStrArray[j], outputFormat));
                        if (i == success)
                            break;
                    }
                }
            }
            i = success;
        }

        return array;
    }

    /**
     * Get a string which all Chinese characters are replaced by corresponding
     * main (first) Hanyu Pinyin representation.
     * <p>
     * <p>
     * <b>Special Note</b>: If the return contains "none0", that means that
     * Chinese character is in Unicode CJK talbe, however, it has not
     * pronounciation in Chinese. <b> This interface will be removed in next
     * release. </b>
     *
     * @param str          A given string contains Chinese characters
     * @param outputFormat Describes the desired format of returned Hanyu Pinyin string
     * @param separate     The string is appended after a Chinese character (excluding
     *                     the last Chinese character at the end of sentence). <b>Note!
     *                     Separate will not appear after a non-Chinese character</b>
     * @param retain       Retain the characters that cannot be converted into pinyin characters
     * @return a String identical to the original one but all recognizable
     * Chinese characters are converted into main (first) Hanyu Pinyin
     * representation
     */
    static public String toHanYuPinyinString(String str, HanyuPinyinOutputFormat outputFormat,
                                             String separate, boolean retain) throws BadHanyuPinyinOutputFormatCombination {
        ChineseToPinyinResource resource = ChineseToPinyinResource.getInstance();
        StringBuilder resultPinyinStrBuf = new StringBuilder();

        int[] codePoints = toCodePointArray(str);

        for (int i = 0; i < codePoints.length; i++) {
            String[] pinyinStrArray = null;//匹配到的最长的结果
            int codePoint = codePoints[i];
            Trie currentTrie = resource.getUnicodeToHanyuPinyinTable();
            int success = i;
            int current = i;
            do {
                String hexStr = hexStr(codePoint);
                currentTrie = currentTrie.get(hexStr);
                if (currentTrie != null) {
                    if (currentTrie.getPinyin() != null) {
                        pinyinStrArray = currentTrie.getPinyinArray();
                        success = current;
                    }
                    currentTrie = currentTrie.getNextTire();
                }
                current++;
                if (current < codePoints.length)
                    codePoint = codePoints[current];
                else
                    break;
            }
            while (currentTrie != null);

            if (pinyinStrArray == null) {//如果在前缀树中没有匹配到，那么它就不能转换为拼音，直接输出或者去掉
                if (retain) resultPinyinStrBuf.appendCodePoint(codePoints[i]);
            } else {
                if (pinyinStrArray != null) {
                    for (int j = 0; j < pinyinStrArray.length; j++) {
                        resultPinyinStrBuf.append(outputFormat==null ? pinyinStrArray[j] : PinyinFormatter.formatHanyuPinyin(pinyinStrArray[j], outputFormat));
                        if (current < codePoints.length || (j < pinyinStrArray.length - 1 && i != success)) {//不是最后一个,(也不是拼音的最后一个,并且不是最后匹配成功的)
                            if(separate!=null) resultPinyinStrBuf.append(separate);
                        }
                        if (i == success)
                            break;
                    }
                }
            }
            i = success;
        }

        return resultPinyinStrBuf.toString();
    }

    private static String hexStr(Integer codePoint) {
        codePoint = ChineseToPinyinResource.tsMap.getOrDefault(codePoint, codePoint);
        return Integer.toHexString(codePoint).toUpperCase();
    }

    static public int[] toCodePointArray(String str) {
        int[] codePointArray = new int[str.codePointCount(0, str.length())];
        for(int i=0,j=0;i<str.length();i=str.offsetByCodePoints(i, 1)){
            codePointArray[j++]=str.codePointAt(i);
        }
        return codePointArray;
    }

    static public HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    static {
        defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
    }

    // ! Hidden constructor
    private PinyinHelper() {
    }
}
