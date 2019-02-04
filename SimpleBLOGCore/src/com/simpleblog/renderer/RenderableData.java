package com.simpleblog.renderer;

import java.io.OutputStream;

import com.simpleblog.entries.Entry;
import com.simpleblog.utils.IOUtil;

public class RenderableData {
	
	private static final String HTML_MIME_TYPE = "text/html";
	
	private final Entry ENTRY;
	
	private final String CONTENT;
	
	private final String MIME_TYPE;
	
	private final boolean EMBEDDABLE;
	
	public RenderableData(Entry entry) {
		ENTRY = entry;
		CONTENT = null;
		MIME_TYPE = IOUtil.getMimeType(entry.getFile());
		EMBEDDABLE = HTML_MIME_TYPE.equals(MIME_TYPE);
	}
	
	public RenderableData(String content, String mimeType, boolean embeddable) {
		ENTRY = null;
		CONTENT = content;
		MIME_TYPE = mimeType;
		EMBEDDABLE = embeddable;
	}
	
	public void writeData(OutputStream out) {
		if (ENTRY != null) {
			IOUtil.sendFileToStream(ENTRY.getFile(), out);
		} else
		if (CONTENT != null) {
			IOUtil.sendStringToStream(CONTENT, out);
		}
	}
	
	public String getMimeType() {
		return MIME_TYPE;
	}
	
	public boolean isEmbeddable() {
		return EMBEDDABLE;
	}
	
	public boolean isNotEmpty() {
		return 
			(ENTRY != null && ENTRY.getFile() != null && ENTRY.getFile().exists() && ENTRY.getFile().length() > 0L) ||
			(CONTENT != null && CONTENT.length() > 0L);
	}
	
	public long getDataLength() {
		long ret = 0L;
		if (ENTRY != null && ENTRY.getFile() != null && ENTRY.getFile().exists()) {
			ret = ENTRY.getFile().length();
		} else 
		if (CONTENT != null) {
			ret = CONTENT.length();
		}
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (EMBEDDABLE ? 1231 : 1237);
		result = prime * result + ((ENTRY == null) ? 0 : ENTRY.hashCode());
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
		RenderableData other = (RenderableData) obj;
		if (EMBEDDABLE != other.EMBEDDABLE)
			return false;
		if (ENTRY == null) {
			if (other.ENTRY != null)
				return false;
		} else if (!ENTRY.equals(other.ENTRY))
			return false;
		if (MIME_TYPE == null) {
			if (other.MIME_TYPE != null)
				return false;
		} else if (!MIME_TYPE.equals(other.MIME_TYPE))
			return false;
		return true;
	}


}
