package com.code.wordcount;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WordCountApplicationMock {

	static Translator translator;

	@BeforeClass
	public static void setUp() {
		translator = mock(Translator.class);
	}

	@Test
	public void test_non_alphabetic() {
		WordCountController wordCountController = new WordCountController(translator);
		wordCountController.addWord("Un624*!@#m()");
		assertEquals(0,wordCountController.getWordCount("Un624*!@#m()"));
	}

	@Test
	public void test_All() {
		// Given
		when(translator.translateWord("Hallo")).thenReturn("Hello");
		when(translator.translateWord("Cette")).thenReturn("This");
		when(translator.translateWord("This")).thenReturn("This");
		when(translator.translateWord("Today")).thenReturn("Today");
		when(translator.translateWord("Dies")).thenReturn("This");
		WordCountController wordCountController = new WordCountController(translator);

		// When
		wordCountController.addWord("Hallo");
		wordCountController.addWord("fg&*()");
		wordCountController.addWord("Dies");
		wordCountController.addWord("Today");
		wordCountController.addWord("Hallo");
		wordCountController.addWord("Cette");
		wordCountController.addWord("This");

		// Then
		assertEquals(2,wordCountController.getWordCount("Hello"));
		assertEquals(3,wordCountController.getWordCount("This"));
		assertEquals(0,wordCountController.getWordCount("fg&*()"));
		assertEquals(1,wordCountController.getWordCount("Today"));
	}

}



