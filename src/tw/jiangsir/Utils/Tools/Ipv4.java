package tw.jiangsir.Utils.Tools;

public class Ipv4 {
	private int[] ip = new int[4];

	private Ipv4() {
	}

	public static Ipv4 newInstance(String ipstring) {
		Ipv4 ipv4 = new Ipv4();
		String[] bb = ipstring.trim().split("\\.");
		int[] ip = new int[4];
		for (int i = 0; i < 4; i++) {
			ip[i] = Integer.parseInt(bb[i].trim());
		}
		ipv4.setIp(ip);
		return ipv4;
	}

	public int[] getIp() {
		return ip;
	}

	public void setIp(int[] ip) {
		this.ip = ip;
	}

	public int toInt() {
		return ((getIp()[3] << 24) & 0xFF000000)
				| ((getIp()[2] << 16) & 0xFF0000)
				| ((getIp()[1] << 8) & 0xFF00) | (getIp()[0] & 0xFF);

	}

	public String toString() {
		return getIp()[3] + "." + getIp()[2] + "." + getIp()[1] + "."
				+ getIp()[0];
	}

}
