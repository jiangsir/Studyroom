package tw.jiangsir.Utils.Tools;

public class Ipv4Cidr {
	private Ipv4 ip = Ipv4.newInstance("127.0.0.1");
	private int mask = 0;

	private Ipv4Cidr() {
	}

	public static Ipv4Cidr newInstance(String cidrstring) {
		Ipv4Cidr ipv4cidr = new Ipv4Cidr();
		if (cidrstring
				.matches("^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\/(3[0-2]|[1-2]?[0-9])$")) {
			return ipv4cidr;
		}

		String[] cidr = cidrstring.split("\\/");
		ipv4cidr.setIp(Ipv4.newInstance(cidr[0]));
		ipv4cidr.setMask(Integer.parseInt(cidr[1]));
		return ipv4cidr;
	}

	public Ipv4 getIp() {
		return ip;
	}

	public void setIp(Ipv4 ip) {
		this.ip = ip;
	}

	public int getMask() {
		return mask;
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	public String toString() {
		return this.getIp().toString() + "/" + this.getMask();
	}

}
