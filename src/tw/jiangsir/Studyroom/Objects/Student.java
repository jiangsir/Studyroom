package tw.jiangsir.Studyroom.Objects;

import tw.jiangsir.Utils.Annotations.Persistent;

public class Student {

	@Persistent(name = "id")
	private Long id = 0L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
