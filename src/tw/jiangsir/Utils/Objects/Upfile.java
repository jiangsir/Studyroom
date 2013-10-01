package tw.jiangsir.Utils.Objects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import tw.jiangsir.Utils.Annotations.Persistent;

public class Upfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6818084467792604935L;

	@Persistent(name = "id")
	private Long id = 0L;
	@Persistent(name = "filename")
	private String filename = "";
	@Persistent(name = "filetype")
	private String filetype = "";
	@Persistent(name = "bytes")
	private byte[] bytes;
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(new Date().getTime());

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
