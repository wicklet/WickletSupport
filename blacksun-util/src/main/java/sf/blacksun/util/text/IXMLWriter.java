/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;

import java.io.Closeable;
import java.util.Map;

/**
 * Basically same as IXmlWriter, but don't implicitly escape text and attribute values, except txt().
 * It don't add line breaks either, call lb() to break up lines.
 */
public interface IXMLWriter extends Closeable {

    String INDENT = "";
    String TAB = "    ";

    ////////////////////////////////////////////////////////////////////////

    public abstract IXMLWriter xmlHeader();

    public abstract IXMLWriter start(String tag);
    public abstract IXMLWriter start(String tag, IAttribute...attrs);
    /** @param attrs An array of key and value pairs (ie. key1, value1, key2, value2, ...). */
    public abstract IXMLWriter start(String tag, String...attrs);
    public abstract IXMLWriter start(String tag, Map<String, String> attrs);
    /** Start n nested tags */
    public abstract IXMLWriter startAll(String...tags);

    public abstract IXMLWriter empty(String tag);
    public abstract IXMLWriter empty(String tag, IAttribute...attrs);
    /** @param attrs An array of key and value pairs (ie. key1, value1, key2, value2, ...). */
    public abstract IXMLWriter empty(String tag, String...attrs);
    public abstract IXMLWriter empty(String tag, Map<String, String> attrs);

    /**
     * Emit the given content, which may be plain text or contains tags, as is.
     * If there are more than one argument, each of the subsequent argument is on a new line,
     * with proper indents.
     */
    public abstract IXMLWriter raw(String content);
    public abstract IXMLWriter raw(String...content);
    public abstract IXMLWriter format(String format, Object...args);
    /** Same as raw(content). */
    public abstract IXMLWriter txt(String content);
    public abstract IXMLWriter txt(String...content);
    /** Same as raw(escXml(content)). */
    public abstract IXMLWriter esc(String content);
    public abstract IXMLWriter esc(String...content);

    /**
     * Emit comment.
     * If there are more than one argument, each of the subsequent argument is on a new line,
     * with proper indents.
     */
    public abstract IXMLWriter comment(String comment);
    public abstract IXMLWriter comment(String...comment);

    /**
     * Emit cdata.
     * If there are more than one argument, each of the subsequent argument is on a new line,
     * with proper indents.
     */
    public abstract IXMLWriter cdata(String cdata);
    public abstract IXMLWriter cdata(String...cdata);

    public abstract IXMLWriter end();
    public abstract IXMLWriter end(String...expects);
    public abstract IXMLWriter end(int level);
    public abstract IXMLWriter endTill(int level);
    public abstract IXMLWriter endAll();

    public abstract IXMLWriter element(String tag, String content);
    public abstract IXMLWriter element(String tag, String content, IAttribute...attrs);
    /** @param attrs An array of key and value pairs (ie. key1, value1, key2, value2, ...). */
    public abstract IXMLWriter element(String tag, String content, String...attrs);
    public abstract IXMLWriter element(String tag, String content, Map<String, String> attrs);

    ////////////////////////////////////////////////////////////////////////

    /** Write a line break. Use for manual formatting. */
    public abstract IXMLWriter lb();
    public abstract IXMLWriter formatted(String...content);
    public abstract IXMLWriter flush();

    ////////////////////////////////////////////////////////////////////////

    public abstract IAttribute a(String name, String value);

    ////////////////////////////////////////////////////////////////////////

    /** @return Current nested level, 0 for top level outside root element. */
    public abstract int level();

    ////////////////////////////////////////////////////////////////////////
}
