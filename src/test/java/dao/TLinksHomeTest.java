package dao;

import pojo.TLinks;
import pojo.TSeed;

public class TLinksHomeTest extends TestBase {
	private TLinksHome linksHome ;

	public TLinksHomeTest() {
		linksHome = getCxt().getBean(TLinksHome.class);
	}
	
	public void testQuery() {
		TSeed instance = new TSeed();
		instance.setId(13);
		TLinks cLinks = new TLinks();
		cLinks.setTSeed(instance);
		System.out.println(linksHome.list(cLinks));
		
		
//		List<TSeed> list = seedHome.findByExample(instance );
//		System.out.println(list.get(0));
//		System.out.println(list.get(0).getTLinkses());
	}

}
