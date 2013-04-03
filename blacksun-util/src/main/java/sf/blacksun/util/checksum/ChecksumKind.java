/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.checksum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum ChecksumKind {
	MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256"), SHA512("SHA-512"); 



	private String digesterId;

	private ChecksumKind(String id) {
		digesterId = id;
	}


	public MessageDigest getDigester() {
		try {
			return MessageDigest.getInstance(digesterId);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
	}}
}
