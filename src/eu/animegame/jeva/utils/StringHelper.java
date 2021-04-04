package eu.animegame.jeva.utils;

import java.util.StringTokenizer;

/**
 *
 * @author radiskull
 */
public class StringHelper {

	public static String join(String[] s, String delimiter) {
		int max = s.length;
		StringBuilder buffer = new StringBuilder(s[0]);
		for (int i = 1; i < max; i++) {
			buffer.append(delimiter).append(s[i]);
		}

		return buffer.toString();
	}

	public static String[] split(String s, String needle) {
		StringTokenizer st = new StringTokenizer(s, needle);
		int n = st.countTokens();
		String[] ret = new String[n];
		for (int i = 0; i < n; i++) {
			ret[i] = st.nextToken();
		}
		return ret;
	}

	public static String replaceWhitespaces(String s) {
		return s.replaceAll("[\\t \\f\\r\\x0B]+", " ").replaceAll("[\\s]*[\\n][\\s]*", "\n");
	}

	public static String replaceWhitespacesLinewise(String s) {
		return s.replaceAll("[\\t \\f\\r\\x0B]+", " ").replaceAll("[\\t \\f\\r\\x0B]*[\\n][\\t \\f\\r\\x0B]*", "\n");
	}

	public static String[] splitNoWhitespaces(String s, String needle) {
		return split(replaceWhitespaces(s), needle);
	}

	public static String joinNoWhitespaces(String[] s, String needle) {
		return replaceWhitespaces(join(s, needle));
	}

}
