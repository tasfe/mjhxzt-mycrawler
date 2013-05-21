package dao;

import pojo.TSeed;

public class TSeedHomeTest extends TestBase {
	private TSeedHome seedHome  = null;
	
	public TSeedHomeTest() {
		seedHome = getCxt().getBean(TSeedHome.class);
	}

	public void testQuery() {
		TSeed instance = new TSeed();
		instance.setId(13);
		TSeed seed = seedHome.findById(13);
		System.out.println(seed.getTLinkses());
		
		
//		List<TSeed> list = seedHome.findByExample(instance );
//		System.out.println(list.get(0));
//		System.out.println(list.get(0).getTLinkses());
	}
	

}
