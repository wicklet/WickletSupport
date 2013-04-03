/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.checksum;







import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

import java.util.regex.Pattern;






public class ChecksumUtil {

	public static final Map<ChecksumKind, Pattern> ChecksumSuffixPatterns = new TreeMap<ChecksumKind, Pattern>();
	private static final ThreadLocal<Map<ChecksumKind, MessageDigest>> digesters
		= new ThreadLocal<Map<ChecksumKind, MessageDigest>>();
	static {
		for (ChecksumKind kind: ChecksumKind.values()) {
			String s = kind.name().toLowerCase();
			Pattern pat = Pattern.compile("(?msi)(.*)\\." + s + "(sum)?(\\.txt)?");
			ChecksumSuffixPatterns.put(kind, pat);
	}}

	public static MessageDigest getDigester(ChecksumKind kind) {
		Map<ChecksumKind, MessageDigest> local = digesters.get();
		if (local == null)
			digesters.set(local = new TreeMap<ChecksumKind, MessageDigest>());
		MessageDigest ret = local.get(kind);
		if (ret == null)
			local.put(kind, ret = kind.getDigester());
		return ret;
	}


	//////////////////////////////////////////////////////////////////////

	public enum Status {
		NONE, OK, FAIL; 
	}

	public static class ErrorInfo {
	}

	//////////////////////////////////////////////////////////////////////
}
