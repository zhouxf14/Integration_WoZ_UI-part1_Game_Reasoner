package edu.usc.ict.vhmsg;

public class Config {
	/**
	 * VHMsg server host
	 */
	//public static String VHMSG_SERVER_URL = "192.168.1.100";
	//public static String VHMSG_SERVER_URL = "192.168.1.122";
	//public static String VHMSG_SERVER_URL = "192.168.1.133";
	//public static String VHMSG_SERVER_URL = "localhost";
	public static String VHMSG_SERVER_URL = "128.237.213.149";
	
	public static String accessKey = "GDNAJRE2KBPMBQCEU2CQ";
	public static String secretKey = "rCDTav82VFzUsmPeSP8jxj+mlFAhhfCT9i71DCsm";
	
	//public static String accessKey = "GDNAJD2EAPYR6GMFUE2A";
	//public static String secretKey = "ZII8cQHvBqaC2LXFfoTeNrYWLIG295udn1zf90RT";
	
	public static final int RUN_ON_WINDOWS = 0;
	public static final int RUN_ON_MAC = 1;
	public static int OPERATION_SYSTEM = RUN_ON_MAC;
	
	public static boolean onCache = false;
	public static String cacheHashMapFile = "resources/cache.tsv";
	
}
