package com.jvmbytes.commons.utils;

import java.util.Collection;
import java.util.Iterator;

import static java.util.regex.Pattern.quote;

/**
 * 字符串操作工具类
 *
 * @author luanjia
 */
public class StringUtils {

    /**
     * 字符串是否为空
     * <p>
     * null和""均为空
     * </p>
     *
     * @param string 目标字符串
     * @return TRUE:空字符串;FALSE:非空字符串
     */
    public static boolean isEmpty(String string) {
        return null == string
                || string.isEmpty();
    }

    /**
     * 通配符表达式匹配
     * <p>
     * 通配符是一种特殊语法，主要有星号(*)和问号(?)组成，主要用来模糊匹配类名和方法名。
     * 比如：java.lang.String，可以被"*String"所匹配
     * </p>
     * <ul>
     * <li>(null) matching (null) == false</li>
     * <li>    ANY matching ("*") == true</li>
     * </ul>
     *
     * @param string   目标字符串
     * @param wildcard 通配符匹配模版
     * @return true:目标字符串符合匹配模版;false:目标字符串不符合匹配模版
     */
    public static boolean matching(final String string, final String wildcard) {
        return null != wildcard
                && null != string
                && matching(string, wildcard, 0, 0);
    }

    /**
     * Internal matching recursive function.
     */
    private static boolean matching(String string, String wildcard, int stringStartNdx, int patternStartNdx) {
        int pNdx = patternStartNdx;
        int sNdx = stringStartNdx;
        int pLen = wildcard.length();

        if (pLen == 1) {
            // speed-up
            if (wildcard.charAt(0) == '*') {
                return true;
            }
        }
        int sLen = string.length();
        boolean nextIsNotWildcard = false;

        while (true) {

            // check if end of string and/or pattern occurred
            // end of string still may have pending '*' callback pattern
            if ((sNdx >= sLen)) {
                while ((pNdx < pLen) && (wildcard.charAt(pNdx) == '*')) {
                    pNdx++;
                }
                return pNdx >= pLen;
            }

            // end of pattern, but not end of the string
            if (pNdx >= pLen) {
                return false;
            }

            // pattern char
            char p = wildcard.charAt(pNdx);

            // perform logic
            if (!nextIsNotWildcard) {

                if (p == '\\') {
                    pNdx++;
                    nextIsNotWildcard = true;
                    continue;
                }
                if (p == '?') {
                    sNdx++;
                    pNdx++;
                    continue;
                }
                if (p == '*') {
                    // next pattern char
                    char pnext = 0;
                    if (pNdx + 1 < pLen) {
                        pnext = wildcard.charAt(pNdx + 1);
                    }
                    // double '*' have the same effect as one '*'
                    if (pnext == '*') {
                        pNdx++;
                        continue;
                    }
                    int i;
                    pNdx++;

                    // find recursively if there is any substring from the end of the
                    // line that matches the rest of the pattern !!!
                    for (i = string.length(); i >= sNdx; i--) {
                        if (matching(string, wildcard, i, pNdx)) {
                            return true;
                        }
                    }
                    return false;
                }
            } else {
                nextIsNotWildcard = false;
            }

            // check if pattern char and string char are equals
            if (p != string.charAt(sNdx)) {
                return false;
            }

            // everything matches for now, continue
            sNdx++;
            pNdx++;
        }
    }

    /**
     * 模式匹配
     *
     * @param patternType 匹配模式
     * @param string      目标字符串
     * @param pattern     模式字符串
     * @return TRUE:匹配成功 / FALSE:匹配失败
     */
    public static boolean patternMatching(final PatternType patternType, final String string,
                                          final String pattern) {
        switch (patternType) {
            case WILDCARD:
                return StringUtils.matching(string, pattern);
            case REGEX:
                return string.matches(pattern);
            default:
                return false;
        }
    }

    /**
     * 将字符串数组转换为正则表达式字符串数组
     *
     * @param stringArray 目标字符串数组
     * @return 正则表达式字符串数组
     */
    public static String[] toRegexQuoteArray(final String[] stringArray) {
        if (null == stringArray) {
            return null;
        }
        final String[] regexQuoteArray = new String[stringArray.length];
        for (int index = 0; index < stringArray.length; index++) {
            regexQuoteArray[index] = quote(stringArray[index]);
        }
        return regexQuoteArray;
    }

    /**
     * 字符串比较
     *
     * @param cs1 字符串1
     * @param cs2 字符串2
     * @return 是否一样
     */
    public static boolean equals(final String cs1, final String cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return cs1.equals(cs2);
    }

    public static final String EMPTY = "";

    /**
     * join collection
     *
     * @param collection 列表
     * @param separator  分隔
     * @return
     */
    public static String join(final Collection<?> collection, final String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }

    /**
     * join iterator
     *
     * @param iterator  列表
     * @param separator 分隔
     * @return
     */
    public static String join(final Iterator<?> iterator, final String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first == null ? EMPTY : first.toString();
        }

        final StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * join array
     *
     * @param objects   数组
     * @param separator 分隔
     * @return
     */
    public static String join(final Object[] objects, final String separator) {
        if (objects == null) {
            return null;
        }
        if (objects.length == 0) {
            return EMPTY;
        }
        if (objects.length == 1) {
            return objects[0] == null ? EMPTY : objects[0].toString();
        }

        final StringBuilder buf = new StringBuilder(256);
        if (objects[0] != null) {
            buf.append(objects[0]);
        }

        for (int i = 1; i < objects.length; i++) {
            if (separator != null) {
                buf.append(separator);
            }
            if (objects[i] != null) {
                buf.append(objects[i]);
            }
        }
        return buf.toString();
    }
}
