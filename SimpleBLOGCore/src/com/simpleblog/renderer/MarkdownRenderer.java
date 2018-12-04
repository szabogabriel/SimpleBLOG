package com.simpleblog.renderer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.simpleblog.CoreConfig;
import com.simpleblog.entries.Entry;

public class MarkdownRenderer implements FileRenderer {
	
	private final File MARKDOWN_FILE;
	private final String COMMAND_PERL ;
	
	public MarkdownRenderer() {
		this(new File(CoreConfig.DIR_TOOLS.toString() + "/Markdown.pl"), CoreConfig.COMMAND_PERL.toString());
	}
	
	public MarkdownRenderer(File markdownFile, String commandPerl) {
		this.MARKDOWN_FILE = markdownFile;
		this.COMMAND_PERL = commandPerl;
	}

	@Override
	public RenderedData render(Entry entry) {
		StringBuilder command = new StringBuilder();
		command
			.append(COMMAND_PERL)
			.append(" ")
			.append(MARKDOWN_FILE.getAbsolutePath())
			.append(" --html4tags ")
			.append(entry.getFile().getAbsolutePath());
		
		StringBuilder ret = new StringBuilder();
		
		try {
			Process p = Runtime.getRuntime().exec(command.toString());
			
			InputStream in = p.getInputStream();
			byte [] buffer = new byte[1024];
			int read;
			
			while ((read = in.read(buffer)) > 0) {
				ret.append(new String(buffer, 0, read));
			}
			
			in.close();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(ret.toString());
		
		return new RenderedData(ret.toString().getBytes(), "text/html");
	}

}
