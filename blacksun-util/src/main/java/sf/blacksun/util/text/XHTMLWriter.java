package sf.blacksun.util.text;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Note: Be sure to call close() to flush the line buffer.
 */
public class XHTMLWriter extends XMLWriter implements IXHTMLWriter {

    ////////////////////////////////////////////////////////////////////////

    public XHTMLWriter(final PrintWriter w) {
        super(w);
    }

    public XHTMLWriter(final PrintWriter w, final String tab) {
        super(w, tab);
    }

    public XHTMLWriter(final PrintWriter w, final String indent, final String tab) {
        super(w, indent, tab);
    }

    protected XHTMLWriter(final String indent, final String tab) {
        super(indent, tab);
    }

    @Override
    protected void setWriter(final PrintWriter w) {
        writer = w;
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    public IAttribute id(final String value) {
        return a("id", value);
    }

    @Override
    public IAttribute css(final String value) {
        return a("class", value);
    }

    @Override
    public IAttribute type(final String value) {
        return a("type", value);
    }

    @Override
    public IAttribute name(final String value) {
        return a("name", value);
    }

    @Override
    public IAttribute href(final String value) {
        return a("href", value);
    }

    @Override
    public IAttribute src(final String value) {
        return a("src", value);
    }

    @Override
    public IAttribute id(final Object value) {
        return a("id", value.toString());
    }

    @Override
    public IAttribute css(final Object value) {
        return a("class", value.toString());
    }

    @Override
    public IAttribute type(final Object value) {
        return a("type", value.toString());
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    public IXHTMLWriter doctype(final DOCTYPE doctype) {
        raw(doctype.toString()).lb();
        return this;
    }

    @Override
    public IXHTMLWriter startXHtmlXmlns(final String lang) {
        if (lang == null) {
            start("html", "xmlns", "http://www.w3.org/1999/xhtml").lb();
        } else {
            start("html", "xmlns", "http://www.w3.org/1999/xhtml", "lang", "en", "xml:lang", "en").lb();
        }
        return this;
    }

    @Override
    public IXHTMLWriter contentType(final String charset) {
        empty("meta", "http-equiv", "Content-Type", "content", "text/html", "charset", charset).lb();
        return this;
    }

    @Override
    public IXHTMLWriter title(final String text) {
        element("title", text).lb();
        return this;
    }

    @Override
    public IXHTMLWriter stylesheet(final String href) {
        empty("link", "rel", "stylesheet", "type", "text/css", "href", href).lb();
        return this;
    }

    @Override
    public IXHTMLWriter javascript(final String...content) {
        start("script", "type", "text/javascript").lb();
        raw(content).lb();
        end().lb();
        return this;
    }

    @Override
    public IXHTMLWriter style(final String...content) {
        start("style", "type", "text/css").lb();
        raw(content).lb();
        end().lb();
        return this;
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    public IXHTMLWriter xmlHeader() {
        super.xmlHeader();
        return this;
    }

    @Override
    public IXHTMLWriter start(final String tag) {
        super.start(tag);
        return this;
    }

    @Override
    public IXHTMLWriter startAll(final String...tags) {
        super.startAll(tags);
        return this;
    }

    @Override
    public IXHTMLWriter start(final String tag, final IAttribute...attrs) {
        super.start(tag, attrs);
        return this;
    }

    @Override
    public IXHTMLWriter start(final String tag, final String...attrs) {
        super.start(tag, attrs);
        return this;
    }

    @Override
    public IXHTMLWriter start(final String tag, final Map<String, String> attrs) {
        super.start(tag, attrs);
        return this;
    }

    @Override
    public IXHTMLWriter empty(final String tag) {
        super.empty(tag);
        return this;
    }

    @Override
    public IXHTMLWriter empty(final String tag, final IAttribute...attrs) {
        super.empty(tag, attrs);
        return this;
    }

    @Override
    public IXHTMLWriter empty(final String tag, final String...attrs) {
        super.empty(tag, attrs);
        return this;
    }

    @Override
    public IXHTMLWriter empty(final String tag, final Map<String, String> attrs) {
        super.empty(tag, attrs);
        return this;
    }

    @Override
    public IXHTMLWriter raw(final String text) {
        super.raw(text);
        return this;
    }

    @Override
    public IXHTMLWriter raw(final String...a) {
        super.raw(a);
        return this;
    }

    @Override
    public IXHTMLWriter txt(final String text) {
        super.txt(text);
        return this;
    }

    @Override
    public IXHTMLWriter txt(final String...a) {
        super.txt(a);
        return this;
    }

    @Override
    public IXHTMLWriter esc(final String text) {
        super.esc(text);
        return this;
    }

    @Override
    public IXHTMLWriter esc(final String...a) {
        super.esc(a);
        return this;
    }

    @Override
    public IXHTMLWriter format(final String format, final Object...args) {
        super.format(format, args);
        return this;
    }

    @Override
    public IXHTMLWriter comment(final String text) {
        super.comment(text);
        return this;
    }

    @Override
    public IXHTMLWriter comment(final String...a) {
        super.comment(a);
        return this;
    }

    @Override
    public IXHTMLWriter cdata(final String text) {
        super.cdata(text);
        return this;
    }

    @Override
    public IXHTMLWriter cdata(final String...a) {
        super.cdata(a);
        return this;
    }

    @Override
    public IXHTMLWriter end() {
        super.end();
        return this;
    }

    @Override
    public IXHTMLWriter end(final String...expected) {
        super.end(expected);
        return this;
    }

    @Override
    public IXHTMLWriter end(final int levels) {
        super.end(levels);
        return this;
    }

    @Override
    public IXHTMLWriter endTill(final int level) {
        super.endTill(level);
        return this;
    }

    @Override
    public IXHTMLWriter endAll() {
        super.endAll();
        return this;
    }

    @Override
    public IXHTMLWriter element(final String tag, final String text) {
        super.element(tag, text);
        return this;
    }

    @Override
    public IXHTMLWriter element(final String tag, final String text, final IAttribute...attrs) {
        super.element(tag, text, attrs);
        return this;
    }

    @Override
    public IXHTMLWriter element(final String tag, final String text, final String...attrs) {
        super.element(tag, text, attrs);
        return this;
    }

    @Override
    public IXHTMLWriter element(final String tag, final String text, final Map<String, String> attrs) {
        super.element(tag, text, attrs);
        return this;
    }

    ////////////////////////////////////////////////////////////////////////

    @Override
    public IXHTMLWriter formatted(final String...a) {
        super.formatted(a);
        return this;
    }

    @Override
    public IXHTMLWriter lb() {
        super.lb();
        return this;
    }

    @Override
    public IXHTMLWriter flush() {
        super.flush();
        return this;
    }

    ////////////////////////////////////////////////////////////////////////
}
