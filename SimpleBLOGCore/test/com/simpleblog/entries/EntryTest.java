package com.simpleblog.entries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import com.simpleblog.renderer.MarkdownRenderer;

public class EntryTest {
	
	private static File testFile = new File("../SimpleBLOGWorkingDirectory/entries/en/Test/TestEntry.md");
	private static Entry testEntry = new Entry(testFile);
	
	@Test
	public void loadEntry() {
		assertEquals(testEntry.getFile().exists(), true);
		assertEquals(testEntry.getType(), EntryTypes.MD);
	}
	
	@Test
	public void render() {
		MarkdownRenderer mdr = new MarkdownRenderer(new File("../SimpleBLOGWorkingDirectory/tools/Markdown.pl"), "perl");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mdr.render(testEntry).writeData(baos);
		String file = baos.toString();
		System.out.println(file);
		assertTrue(file.startsWith("<h1>"));
		assertTrue(file.trim().endsWith("</p>"));
	}
	
	@Test
	public void entriesLoaderTest() {
		EntriesManager el = new EntriesManager(new File("../SimpleBLOGWorkingDirectory/entries"));
		String[] categories = el.getEntryCategories("en");
		assertEquals(categories.length, 1);
		assertEquals(categories[0], "Test");
		
		File [] data = el.getEntries("en", categories[0]);
		assertTrue(Arrays.asList(data[0]).size() > 0);
	}

}
