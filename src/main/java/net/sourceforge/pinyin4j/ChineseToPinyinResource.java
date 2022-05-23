/**
 * This file is part of pinyin4j (http://sourceforge.net/projects/pinyin4j/) and distributed under
 * GNU GENERAL PUBLIC LICENSE (GPL).
 * <p/>
 * pinyin4j is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * <p/>
 * pinyin4j is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with pinyin4j.
 */

/**
 *
 */
package net.sourceforge.pinyin4j;

import net.sourceforge.pinyin4j.multipinyin.Trie;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Manage all external resources required in PinyinHelper class.
 *
 * @author Li Min (xmlerlimin@gmail.com)
 */
class ChineseToPinyinResource {
    /**
     * A hash table contains <Unicode, HanyuPinyin> pairs
     */
    private Trie unicodeToHanyuPinyinTable = null;

    /**
     * @param unicodeToHanyuPinyinTable The unicodeToHanyuPinyinTable to set.
     */
    private void setUnicodeToHanyuPinyinTable(Trie unicodeToHanyuPinyinTable) {
        this.unicodeToHanyuPinyinTable = unicodeToHanyuPinyinTable;
    }

    /**
     * @return Returns the unicodeToHanyuPinyinTable.
     */
    Trie getUnicodeToHanyuPinyinTable() {
        return unicodeToHanyuPinyinTable;
    }

    /**
     * Private constructor as part of the singleton pattern.
     */
    private ChineseToPinyinResource() {
        initializeResource();
    }

    /**
     * Initialize a hash-table contains <Unicode, HanyuPinyin> pairs
     */
    private void initializeResource() {
        try {
            final String resourceName = "/pinyindb/unicode_to_hanyu_pinyin.txt";
            final String resourceMultiName = "/pinyindb/multi_pinyin.txt";

            setUnicodeToHanyuPinyinTable(new Trie());
            getUnicodeToHanyuPinyinTable().load(ResourceHelper.getResourceInputStream(resourceName));

            getUnicodeToHanyuPinyinTable().loadMultiPinyin(ResourceHelper.getResourceInputStream(resourceMultiName));

            getUnicodeToHanyuPinyinTable().loadMultiPinyinExtend();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    Trie getHanyuPinyinTrie(char ch) {

        String codepointHexStr = Integer.toHexString((int) ch).toUpperCase();

        // fetch from hashtable
        return getUnicodeToHanyuPinyinTable().get(codepointHexStr);
    }

    /**
     * Get the unformatted Hanyu Pinyin representations of the given Chinese
     * character in array format.
     *
     * @param ch given Chinese character in Unicode
     * @return The Hanyu Pinyin strings of the given Chinese character in array
     * format; return null if there is no corresponding Pinyin string.
     */
    String[] getHanyuPinyinStringArray(char ch) {
        Trie trie = getHanyuPinyinTrie(ch);
        return trie==null ? null : trie.getPinyinArray();
    }

    /**
     * Singleton factory method.
     *
     * @return the one and only MySingleton.
     */
    static ChineseToPinyinResource getInstance() {
        return ChineseToPinyinResourceHolder.theInstance;
    }

    /**
     * Singleton implementation helper.
     */
    private static class ChineseToPinyinResourceHolder {
        static final ChineseToPinyinResource theInstance = new ChineseToPinyinResource();
    }
}
