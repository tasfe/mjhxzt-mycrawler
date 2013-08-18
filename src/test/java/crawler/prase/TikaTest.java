package crawler.prase;

import java.io.Reader;
import java.net.URL;

import org.apache.tika.Tika;

public class TikaTest {
	public static void main(String[] args) throws Exception {
		Tika tika = new Tika();
		URL url = new URL("http://www.baidu.com");
		Reader reader =  tika.parse(url);
//		char[] c  = null;
		int c =0;
		while ((c=reader.read())>-1) {
			System.out.print((char)c);
		}
	}
}
