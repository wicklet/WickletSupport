package sf.blacksun.util.text;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * Note: Be sure to call close() to flush the line buffer.
 */
public class XMLWriter implements IXMLWriter {

    ////////////////////////////////////////////////////////////////////////

    protected PrintWriter writer;
    private String initialIndent = "";
    private String tab = null;
    private final StringBuilder line = new StringBuilder();
    private int startLevel = 0;
    private final Stack<String> stack = new Stack<String>();

    ////////////////////////////////////////////////////////////////////////

    public XMLWriter(final PrintWriter w) {
        this(w, INDENT, TAB);
    }

    public XMLWriter(final PrintWriter w, final String tab) {
        this(w, INDENT, TAB);
    }

    /**
     * @param indent    Initial indent.
     * @param tab
     */
    public XMLWriter(final PrintWriter w, final String indent, final String tab) {
        writer = w;
        initialIndent = indent;
        this.tab = tab;
    }

    /**
     * @param indent    Initial indent.
     * @param tab
     */
    protected XMLWriter(final String indent, final String tab) {
        initialIndent = indent;
        this.tab = tab;
    }

    protected void setWriter(final PrintWriter w) {
        writer = w;
    }

    ////////////////////////////////////////////////////////////////////////

    public static String escXml(final CharSequence text) {
        return XmlUtil.escXml(text).toString();
    }

    public static String[] escXml(final CharSequence...a) {
        final int len = a.length;
        final String[] ret = new String[len];
        for (int i = 0; i < len; ++i) {
            ret[i] = XmlUtil.escXml(a[i]).toString();
        }
        return ret;
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    public IXMLWriter xmlHeader() {
        raw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").lb();
        return this;
    }

    @Override
    public IXMLWriter start(final String tag) {
        line.append("<");
        line.append(tag);
        line.append(">");
        stack.push(tag);
        return this;
    }

    @Override
    public IXMLWriter startAll(final String...tags) {
        for ( final String tag: tags) {
            line.append("<");
            line.append(tag);
            line.append(">");
            stack.push(tag);
        }
        return this;
    }

    @Override
    public IXMLWriter start(final String tag, final IAttribute...attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append(">");
        stack.push(tag);
        return this;
    }

    @Override
    public IXMLWriter start(final String tag, final String...attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append(">");
        stack.push(tag);
        return this;
    }

    @Override
    public IXMLWriter start(final String tag, final Map<String, String> attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append(">");
        stack.push(tag);
        return this;
    }

    @Override
    public IXMLWriter empty(final String tag) {
        line.append("<");
        line.append(tag);
        line.append("/>");
        return this;
    }

    @Override
    public IXMLWriter empty(final String tag, final IAttribute...attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append("/>");
        return this;
    }

    @Override
    public IXMLWriter empty(final String tag, final String...attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append("/>");
        return this;
    }

    @Override
    public IXMLWriter empty(final String tag, final Map<String, String> attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append("/>");
        return this;
    }

    @Override
    public IXMLWriter raw(final String text) {
        if (text != null) {
            line.append(text);
        }
        return this;
    }

    @Override
    public IXMLWriter raw(final String...a) {
        for (int i = 0, len = a.length; i < len; ++i) {
            if (a[i] != null) {
                if (i > 0) {
                    lb();
                }
                line.append(a[i]);
        }}
        return this;
    }

    @Override
    public IXMLWriter txt(final String text) {
        return raw(text);
    }

    @Override
    public IXMLWriter txt(final String...a) {
        return raw(a);
    }

    @Override
    public IXMLWriter esc(final String text) {
        return raw(escXml(text));
    }

    @Override
    public IXMLWriter esc(final String...a) {
        return raw(escXml(a));
    }

    @Override
    public IXMLWriter format(final String format, final Object...args) {
        line.append(String.format(format, args));
        return this;
    }

    @Override
    public IXMLWriter comment(final String text) {
        line.append("<!-- ");
        if (text != null) {
            line.append(text);
        }
        line.append(" -->");
        return this;
    }

    @Override
    public IXMLWriter comment(final String...a) {
        line.append("<!-- ");
        for (int i = 0, len = a.length; i < len; ++i) {
            if (a[i] != null) {
                if (i > 0) {
                    lb();
                }
                line.append(a[i]);
        }}
        line.append(" -->");
        return this;
    }

    @Override
    public IXMLWriter cdata(final String text) {
        line.append("<![CDATA[");
        if (text != null) {
            line.append(text);
        }
        line.append("]]>");
        return this;
    }

    @Override
    public IXMLWriter cdata(final String...a) {
        line.append("<![CDATA[");
        for (int i = 0, len = a.length; i < len; ++i) {
            if (a[i] != null) {
                if (i > 0) {
                    lb();
                }
                line.append(a[i]);
        }}
        line.append("]]>");
        return this;
    }

    @Override
    public IXMLWriter end() {
        line.append("</");
        line.append(stack.pop());
        line.append(">");
        return this;
    }

    @Override
    public IXMLWriter end(final String...expected) {
        for ( final String tag: expected) {
            end1(tag);
        }
        return this;
    }

    @Override
    public IXMLWriter end(int levels) {
        while (--levels >= 0) {
            end();
        }
        return this;
    }

    @Override
    public IXMLWriter endTill(final int level) {
        while (stack.size() > level) {
            end();
        }
        return this;
    }

    @Override
    public IXMLWriter endAll() {
        while (stack.size() > 0) {
            end();
        }
        return this;
    }

    @Override
    public IXMLWriter element(final String tag, final String text) {
        line.append("<");
        line.append(tag);
        line.append(">");
        if (text != null) {
            line.append(text);
        }
        line.append("</");
        line.append(tag);
        line.append(">");
        return this;
    }

    @Override
    public IXMLWriter element(final String tag, final String text, final IAttribute...attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append(">");
        if (text != null) {
            line.append(text);
        }
        line.append("</");
        line.append(tag);
        line.append(">");
        return this;
    }

    @Override
    public IXMLWriter element(final String tag, final String text, final String...attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append(">");
        if (text != null) {
            line.append(text);
        }
        line.append("</");
        line.append(tag);
        line.append(">");
        return this;
    }

    @Override
    public IXMLWriter element(final String tag, final String text, final Map<String, String> attrs) {
        line.append("<");
        line.append(tag);
        attributes(attrs);
        line.append(">");
        if (text != null) {
            line.append(text);
        }
        line.append("</");
        line.append(tag);
        line.append(">");
        return this;
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    public IAttribute a(final String name, final String value) {
        return new XmlUtil.Attribute(name, value);
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    public IXMLWriter lb() {
        printIndent();
        writer.println(line);
        line.setLength(0);
        startLevel = level();
        return this;
    }

    @Override
    public IXMLWriter formatted(final String...a) {
        if (line.length() > 0) {
            lb();
        }
        for (int i = 0, len = a.length; i < len; ++i) {
            writer.print(a[i]);
        }
        return this;
    }

    @Override
    public IXMLWriter flush() {
        writer.flush();
        return this;
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    public int level() {
        return stack.size();
    }

    @Override
    public void close() {
        if (line.length() > 0) {
            lb();
        }
        writer.close();
    }

    ////////////////////////////////////////////////////////////////////////

    protected StringBuilder getLineBuffer() {
        return line;
    }

    protected void printIndent() {
        writer.print(initialIndent);
        if (tab != null) {
            int level = level();
            if (level > startLevel) {
                level = startLevel;
            }
            while (--level >= 0) {
                writer.print(tab);
    }}}

    ////////////////////////////////////////////////////////////////////////

    private void end1(final String expected) {
        final String tag = stack.pop();
        if (!tag.equals(expected)) {
            throw new RuntimeException("ERROR: Mismatched end tag: expected=" + expected + ", actual=" + tag);
        }
        line.append("</");
        line.append(tag);
        line.append(">");
    }

    private void attributes(final IAttribute...attrs) {
        if (attrs != null) {
            for ( final IAttribute attr: attrs) {
                if (attr != null) {
                    attribute(attr.name(), attr.value());
    }}}}

    private void attributes(final String...attrs) {
        if (attrs != null) {
            for (int i = 0; i < attrs.length; i += 2) {
                attribute(attrs[i], attrs[i + 1]);
    }}}

    private void attributes(final Map<String, String> attrs) {
        if (attrs != null) {
            for ( final Entry<String, String> e: attrs.entrySet()) {
                attribute(e.getKey(), e.getValue());
    }}}

    private void attribute(final String name, final String value) {
        if (name != null) {
            line.append(" ");
            line.append(name);
            line.append("=\"");
            line.append((value == null ? "" : value));
            line.append("\"");
    }}

    ////////////////////////////////////////////////////////////////////////
}
