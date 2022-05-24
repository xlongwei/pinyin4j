package net.sourceforge.pinyin4j.test;

import java.util.Arrays;

import org.junit.Test;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinyinTest {
    @Test
    public void test() throws Exception {
        String str = "𠀛𠀝重慶𠐊〇";
        System.out.println(Arrays.toString(PinyinHelper.toHanYuPinyinStringArray(str, null)));
        System.out.println(PinyinHelper.toHanYuPinyinString(str, PinyinHelper.defaultFormat, null, true));
        System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray("重".codePointAt(0))));
        System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray('乐')));
    }
}
