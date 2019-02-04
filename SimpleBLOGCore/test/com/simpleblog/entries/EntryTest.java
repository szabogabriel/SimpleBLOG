package com.simpleblog.entries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mdr.render(testEntry).writeData(baos);
		String file = baos.toString();
		System.out.println(file);
		assertTrue(file.startsWith("<h1>"));
		assertTrue(file.trim().endsWith("</p>"));
	}
	
	@Test
	public void entriesLoaderTest() {
		EntriesManager el = new EntriesManager(new File("./resources/entries"));
		String[] categories = el.getEntryCategories();
		assertEquals(categories.length, 1);
		assertEquals(categories[0], "Bash");
		
		File [] data = el.getEntries(categories[0]);
		assertEquals(data.length, 1);
		assertEquals(data[0].getName(), "History.md");
	}

}
