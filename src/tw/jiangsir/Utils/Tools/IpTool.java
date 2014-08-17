package tw.jiangsir.Utils.Tools;

public class IpTool {

	public static boolean isSubnet(Ipv4 ipv4, String CIDR) {
		String[] cidr = CIDR.split("/");
		int range = Ipv4.newInstance(cidr[0]).toInt();

		// Step 2. Get CIDR mask
		int mask = (-1) << (32 - Integer.parseInt(cidr[1]));

		// Step 3. Find lowest IP address
		int lowest = range & mask;

		// Step 4. Find highest IP address
		int highest = lowest + (~mask);

		return ipv4.toInt() <= highest && ipv4.toInt() >= lowest;
	}
}
