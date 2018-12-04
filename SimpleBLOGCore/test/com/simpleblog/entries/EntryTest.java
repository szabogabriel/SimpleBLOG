package com.simpleblog.entries;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.simpleblog.renderer.MarkdownRenderer;

public class EntryTest {
	
	private static File testFile = new File("resources/entries/Bash/History.md");
	private static Entry testEntry = new Entry(testFile);
	
	@Test
	public void loadEntry() {
		assertEquals(testEntry.getFile().exists(), true);
		assertEquals(testEntry.getType(), EntryTypes.MD);
	}
	
	@Test
	public void render() {
		MarkdownRenderer mdr = new MarkdownRenderer(new File("./resources/tools/Markdown.pl"), "perl");
		String file = new String(mdr.render(testEntry).getData());
		System.out.println(file);
		assertTrue(file.startsWith("<h1>"));
		assertTrue(file.trim().endsWith("</p>"));
	}
	
	@Test
	public void entriesLoaderTest() {
		EntriesLoader el = new EntriesLoader(new File("./resources/entries"));
		String[] categories = el.getEntryCategories();
		assertEquals(categories.length, 1);
		assertEquals(categories[0], "Bash");
		
		File [] data = el.getEntries(categories[0]);
		assertEquals(data.length, 1);
		assertEquals(data[0].getName(), "History.md");
	}

}
