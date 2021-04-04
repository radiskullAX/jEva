/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.animegame.jeva.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author radiskull
 */
public class RegexHelper {

	public static String parseString(Pattern pattern, String data) {
		return parse(pattern, data, 1);
	}

	public static String parseString(Pattern pattern, String data, int group) {
		return parse(pattern, data, group);
	}

	public static String[] parseStringGroups(Pattern pattern, String data) {
		return parse(pattern, data);
	}

	public static boolean matches(Pattern pattern, String data) {
		return pattern.matcher(data).matches();
	}

	private static String parse(Pattern pattern, String data, int group) {
		Matcher matcher = pattern.matcher(data);
		if (matcher.matches()) {
			return matcher.group(group);
		} else {
			return "";
		}
	}

	private static String[] parse(Pattern pattern, String data) {
		Matcher matcher = pattern.matcher(data);
		if (matcher.matches()) {
			int max = matcher.groupCount();
			String[] result = new String[max];
			for (int i = 1; i < max + 1; i++) {
				result[i - 1] = matcher.group(i);
			}
			return result;
		} else {
			return new String[0];
		}
	}
}