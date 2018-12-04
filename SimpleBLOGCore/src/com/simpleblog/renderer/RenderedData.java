package com.simpleblog.renderer;

import java.util.Arrays;

public class RenderedData {
	
	private final byte [] DATA;
	
	private final String MIME_TYPE;
	
	public RenderedData(byte [] data, String mimeType) {
		DATA = data;
		MIME_TYPE = mimeType;
	}
	
	public byte[] getData() {
		return DATA;
	}
	
	public String getMimeType() {
		return MIME_TYPE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(DATA);
		result = prime * result + ((MIME_TYPE == null) ? 0 : MIME_TYPE.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenderedData other = (RenderedData) obj;
		if (!Arrays.equals(DATA, other.DATA))
			return false;
		if (MIME_TYPE == null) {
			if (other.MIME_TYPE != null)
				return false;
		} else if (!MIME_TYPE.equals(other.MIME_TYPE))
			return false;
		return true;
	}
	
	

}
