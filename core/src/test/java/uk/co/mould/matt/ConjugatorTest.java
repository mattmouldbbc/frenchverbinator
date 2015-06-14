package uk.co.mould.matt;

import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.*;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

import static org.junit.Assert.assertEquals;

public final class ConjugatorTest {

	private final Conjugator conjugator;

	public ConjugatorTest() throws ParserConfigurationException, SAXException, IOException {
		conjugator = new Conjugator(
				new VerbTemplateParser(new InputSource(new FileInputStream("res/verbs-fr.xml"))),
				new ConjugationParser(new InputSource(new FileInputStream("res/conjugation-fr.xml"))));
	}

	@Test
	public void testThat_Aimer_Present_FirstPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("J'aime"), conjugator.getPresentConjugationOf(new QuestionVerb("aimer", null), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Tu aimes"), conjugator.getPresentConjugationOf(new QuestionVerb("aimer", null), Persons.SECOND_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonSingularIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Il aime"), conjugator.getPresentConjugationOf(new QuestionVerb("aimer", null), Persons.THIRD_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aimer_Present_FirstPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Nous aimons"), conjugator.getPresentConjugationOf(new QuestionVerb("aimer", null), Persons.FIRST_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_SecondPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Vous aimez"), conjugator.getPresentConjugationOf(new QuestionVerb("aimer", null), Persons.SECOND_PERSON_PLURAL));
	}

	@Test
	public void testThat_Aimer_Present_ThirdPersonPluralIsConjugatedAsExpected() {
		ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugator.getPresentConjugationOf(new QuestionVerb("aimer", null), Persons.THIRD_PERSON_PLURAL);
		assertEquals(new ConjugatedVerbWithPronoun("Ils aiment"), conjugatedVerbWithPronoun);
	}

	@Test
	public void testThat_Perdre_Present_FirstPersonPluralIsConjugatedAsExpected() {
		assertEquals(new ConjugatedVerbWithPronoun("Je perds"), conjugator.getPresentConjugationOf(new QuestionVerb("perdre", null), Persons.FIRST_PERSON_SINGULAR));
	}

	@Test
	public void testThat_Aller_Present_ThirdPersonPluralIsConjugatedAsExpected() {
		ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugator.getPresentConjugationOf(new QuestionVerb("aller", null), Persons.THIRD_PERSON_SINGULAR);
		assertEquals(new ConjugatedVerbWithPronoun("Il va"), conjugatedVerbWithPronoun);
	}
}
