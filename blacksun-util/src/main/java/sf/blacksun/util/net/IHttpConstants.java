/*
 * The software in this package is distributed under the GNU General Public
 * License version 2, as published by the Free Software Foundation, but with
 * the Classpath exception.  You should have received a copy of the GNU General
 * Public License (GPL) and the Classpath exception along with this program.
 */
package sf.blacksun.util.net;

public interface IHttpConstants {

	public interface HttpHeader {
	}

	public enum HttpStatus {
		Continue(100, "Continue"),
		SwitchingProtocols(101, "Switching Protocols"),
		OK(200, "OK"),
		Created(201, "Created"),
		Accepted(202, "Accepted"),
		NonAuthoritativeInformation(203, "Non-Authoritative Information"),
		NoContent(204, "No Content"),
		ResetContent(205, "Reset Content"),
		PartialContent(206, "Partial Content"),
		MultipleChoices(300, "Multiple Choices"),
		MovedPermanently(301, "Moved Permanently"),
		MovedTemporarily(302, "Moved Temporarily"),
		SeeOther(303, "See Other"),
		NotModified(304, "Not Modified"),
		UseProxy(305, "Use Proxy"),
		BadRequest(400, "Bad Request"),
		Unauthorized(401, "Unauthorized"),
		PaymentRequired(402, "Payment Required"),
		Forbidden(403, "Forbidden"),
		NotFound(404, "Not Found"),
		MethodNotAllowed(405, "Method Not Allowed"),
		NotAcceptable(406, "Not Acceptable"),
		ProxyAuthenticationRequired(407, "Proxy Authentication Required"),
		RequestTimeout(408, "Request Timeout"),
		Conflict(409, "Conflict"),
		Gone(410, "Gone"),
		LengthRequired(411, "Length Required"),
		PreconditionFailed(412, "Precondition Failed"),
		RequestEntityTooLarge(413, "Request Entity Too Large"),
		RequestURITooLong(414, "Request-URI Too Long"),
		UnsupportedMediaType(415, "Unsupported Media Type"),
		InternalServerError(500, "Internal Server Error"),
		NotImplemented(501, "Not Implemented"),
		BadGateway(502, "Bad Gateway"),
		ServiceUnavailable(503, "Service Unavailable"),
		GatewayTimeout(504, "Gateway Timeout"),
		HTTPVersionNotSupported(505, "HTTP Version Not Supported");

		private final int code;
		private final String msg;

		HttpStatus(final int code, final String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int code() {
			return code;
		}
	}
}
