package crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReglarTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s ="http://s.taobao.com/search?cd=false&cps=yes&initiative_id=staobaoz_20130520&pdc=true&promote=0&q=%C1%B9%D0%AC+%C5%AE&refpid=420460_1006&source=tbsy&spm=1.186823.220544.1&style=grid&tab=all&v=auction";
//		System.out.println(s.matches("^http://s.taobao.com/search\\?spm=(.+)&q=(.+)&style=(.+)$"));
//		System.out.println(URLDecoder.decode("%C5%AE%B0%EB%C9%ED%C8%B9","gbk"));
		Pattern filters = Pattern.compile("^http://s.taobao.com/search\\?(.*)(&?)q=(.*?)(&.*?)source=tbsy(.*)$");
		Matcher m  = filters.matcher(s);
		if (m.matches()) {
			System.out.println(m.group(3));
		}
	}
}
