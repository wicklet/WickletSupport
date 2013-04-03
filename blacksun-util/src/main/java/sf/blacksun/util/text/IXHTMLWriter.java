/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.text;

import java.util.Map;

/**
 * Basically same as IXmlWriter, but don't implicitly escape text and attribute values, except txt().
 * It don't add line breaks either, call lb() to break up lines.
 */
public interface IXHTMLWriter extends IXMLWriter {

    ////////////////////////////////////////////////////////////////////////

    String DOCTYPE_HTML_40_TRANSITIONAL = "<!DOCTYPE PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";
    String DOCTYPE_HTML_40_TRANSITIONAL_DTD = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\""
        + " \"http://www.w3.org/TR/REC-html40/loose.dtd\">";
    String DOCTYPE_XHTML_10_STRICT = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\">";
    String DOCTYPE_XHTML_10_STRICT_DTD = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""
        + "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
    String ISO_8859_1 = "ISO-8859-1";
    String UTF_8 = "UTF-8";

    ////////////////////////////////////////////////////////////////////////

    public abstract IAttribute id(String value);
    public abstract IAttribute css(String value);
    public abstract IAttribute type(String value);
    public abstract IAttribute name(String value);
    public abstract IAttribute href(String value);
    public abstract IAttribute src(String value);

    public abstract IAttribute id(Object value);
    public abstract IAttribute css(Object value);
    public abstract IAttribute type(Object value);

    ////////////////////////////////////////////////////////////////////////

    /** Write doctype declaration for XHTML 1.0 */
    public abstract IXHTMLWriter doctype(DOCTYPE doctype);

    /** Start html element with xhtml namespace attribute. */
    public abstract IXHTMLWriter startXHtmlXmlns(String lang);

    /** Write title, escaping text. */
    public abstract IXHTMLWriter title(String text);

    /** Write link element for the given stylesheet. */
    public abstract IXHTMLWriter stylesheet(String href);

    /** Write inline javascripts. */
    public abstract IXHTMLWriter javascript(String...content);

    /** Write inline styles. */
    public abstract IXHTMLWriter style(String...content);

    /** Writer meta element for the given content-type. */
    public abstract IXHTMLWriter contentType(String charset);

    ////////////////////////////////////////////////////////////////////////

    @Override
    public abstract IXHTMLWriter xmlHeader();
    @Override
    public abstract IXHTMLWriter start(String tag);
    @Override
    public abstract IXHTMLWriter start(String tag, IAttribute...attrs);
    @Override
    public abstract IXHTMLWriter start(String tag, String...attrs);
    @Override
    public abstract IXHTMLWriter start(String tag, Map<String, String> attrs);
    @Override
    public abstract IXHTMLWriter startAll(String...tags);
    @Override
    public abstract IXHTMLWriter empty(String tag);
    @Override
    public abstract IXHTMLWriter empty(String tag, IAttribute...attrs);
    @Override
    public abstract IXHTMLWriter empty(String tag, String...attrs);
    @Override
    public abstract IXHTMLWriter empty(String tag, Map<String, String> attrs);
    @Override
    public abstract IXHTMLWriter raw(String content);
    @Override
    public abstract IXHTMLWriter raw(String...content);
    @Override
    public abstract IXHTMLWriter txt(String content);
    @Override
    public abstract IXHTMLWriter txt(String...content);
    @Override
    public abstract IXHTMLWriter format(String format, Object...args);
    @Override
    public abstract IXHTMLWriter comment(String comment);
    @Override
    public abstract IXHTMLWriter comment(String...comment);
    @Override
    public abstract IXHTMLWriter cdata(String cdata);
    @Override
    public abstract IXHTMLWriter cdata(String...cdata);
    @Override
    public abstract IXHTMLWriter end();
    @Override
    public abstract IXHTMLWriter end(String...expects);
    @Override
    public abstract IXHTMLWriter end(int level);
    @Override
    public abstract IXHTMLWriter endTill(int level);
    @Override
    public abstract IXHTMLWriter endAll();
    @Override
    public abstract IXHTMLWriter element(String tag, String content);
    @Override
    public abstract IXHTMLWriter element(String tag, String content, IAttribute...attrs);
    @Override
    public abstract IXHTMLWriter element(String tag, String content, String...attrs);
    @Override
    public abstract IXHTMLWriter element(String tag, String content, Map<String, String> attrs);
    @Override
    public abstract IXHTMLWriter lb();
    @Override
    public abstract IXHTMLWriter formatted(String...content);
    @Override
    public abstract IXHTMLWriter flush();

    ////////////////////////////////////////////////////////////////////////

}
