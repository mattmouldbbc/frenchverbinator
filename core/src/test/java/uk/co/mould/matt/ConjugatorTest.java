package uk.co.mould.matt;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.ConjugatedVerbWithPronoun;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.data.tenses.FutureIndicative;
import uk.co.mould.matt.data.tenses.ImperfectIndicative;
import uk.co.mould.matt.data.tenses.PerfectIndicative;
import uk.co.mould.matt.data.tenses.PresentConditional;
import uk.co.mould.matt.data.tenses.PresentIndicative;
import uk.co.mould.matt.data.tenses.PresentSubjunctive;
import uk.co.mould.matt.exceptions.CantConjugateException;
import uk.co.mould.matt.parser.ConjugationParser;
import uk.co.mould.matt.parser.VerbTemplateParser;

import static org.junit.Assert.assertEquals;

public final class ConjugatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Conjugator conjugator;

    public ConjugatorTest() throws ParserConfigurationException, SAXException, IOException {
        conjugator = new Conjugator(
                new VerbTemplateParser(new InputSource(new FileInputStream("res/verbs-fr.xml"))),
                new ConjugationParser(
                        new InputSource(new FileInputStream("res/conjugation-fr.xml"))));
    }

    @Test
    public void testThat_Aimer_Present_FirstPersonSingularIsConjugatedAsExpected() throws uk.co.mould.matt.exceptions.CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("J'aime"),
                conjugator.getConjugationOf(new InfinitiveVerb("aimer", null, "avoir"),
                        Persons.FIRST_PERSON_SINGULAR,
                        new PresentIndicative()));
    }

    @Test
    public void testThat_Aimer_Present_SecondPersonSingularIsConjugatedAsExpected() throws uk.co.mould.matt.exceptions.CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Tu aimes"),
                conjugator.getConjugationOf(new InfinitiveVerb("aimer", null, "avoir"),
                        Persons.SECOND_PERSON_SINGULAR,
                        new PresentIndicative()));
    }

    @Test
    public void testThat_Aimer_Present_ThirdPersonSingularIsConjugatedAsExpected() throws uk.co.mould.matt.exceptions.CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Il aime"),
                conjugator.getConjugationOf(new InfinitiveVerb("aimer", null, "avoir"),
                        Persons.THIRD_PERSON_SINGULAR,
                        new PresentIndicative()));
    }

    @Test
    public void testThat_Aimer_Present_FirstPersonPluralIsConjugatedAsExpected() throws uk.co.mould.matt.exceptions.CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Nous aimons"),
                conjugator.getConjugationOf(new InfinitiveVerb("aimer", null,  "avoir"),
                        Persons.FIRST_PERSON_PLURAL,
                        new PresentIndicative()));
    }

    @Test
    public void testThat_Aimer_Present_SecondPersonPluralIsConjugatedAsExpected() throws uk.co.mould.matt.exceptions.CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Vous aimez"),
                conjugator.getConjugationOf(new InfinitiveVerb("aimer", null,  "avoir"),
                        Persons.SECOND_PERSON_PLURAL,
                        new PresentIndicative()));
    }

    @Test
    public void testThat_Aimer_Present_ThirdPersonPluralIsConjugatedAsExpected() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugator.getConjugationOf(
                new InfinitiveVerb("aimer", null,  "avoir"), Persons.THIRD_PERSON_PLURAL,
                new PresentIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Ils aiment"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThat_Perdre_Present_FirstPersonPluralIsConjugatedAsExpected() throws uk.co.mould.matt.exceptions.CantConjugateException {
        assertEquals(new ConjugatedVerbWithPronoun("Je perds"),
                conjugator.getConjugationOf(new InfinitiveVerb("perdre", null,  "avoir"),
                        Persons.FIRST_PERSON_SINGULAR,
                        new PresentIndicative()));
    }

    @Test
    public void testThat_Aller_Present_ThirdPersonPluralIsConjugatedAsExpected() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("aller", null,  "avoir"),
                        Persons.THIRD_PERSON_SINGULAR,
                        new PresentIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Il va"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatIfVerbIsUnknownNullExceptionIsThrown() throws uk.co.mould.matt.exceptions.CantConjugateException {
        thrown.expect(uk.co.mould.matt.exceptions.CantConjugateException.class);
        conjugator.getConjugationOf(new InfinitiveVerb("some nonexistent verb", null, "avoir"),
                Persons.THIRD_PERSON_SINGULAR,
                new PresentIndicative());
    }

    @Test
    public void testThatImperfectTenseCanBeConjugated() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("savoir", null,  "avoir"),
                        Persons.THIRD_PERSON_PLURAL,
                        new ImperfectIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Ils savaient"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatFutureTenseCanBeConjugated() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("écrire", null,  "avoir"),
                        Persons.SECOND_PERSON_SINGULAR,
                        new FutureIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Tu écriras"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatSubjunctivePresentCanBeConjugated() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("être", null,  "avoir"),
                        Persons.THIRD_PERSON_SINGULAR,
                        new PresentSubjunctive());
        assertEquals(new ConjugatedVerbWithPronoun("Il soit"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatConditionalPresentCanBeConjugated() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("vouloir", null,  "avoir"),
                        Persons.SECOND_PERSON_SINGULAR,
                        new PresentConditional());
        assertEquals(new ConjugatedVerbWithPronoun("Tu voudrais"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatPerfectTenseCanBeFormedWithAvoir() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("vouloir", null,  "avoir"),
                        Persons.SECOND_PERSON_SINGULAR,
                        new PerfectIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Tu as voulu"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatPerfectTenseWithOnePerfectParticipleCanBeFormedWithAvoir() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("être", null,  "avoir"),
                        Persons.FIRST_PERSON_PLURAL,
                        new PerfectIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Nous avons été"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatPerfectTenseCanBeFormedWithEtreForSingularPerson() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("aller", null, "être"),
                        Persons.FIRST_PERSON_SINGULAR,
                        new PerfectIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Je suis allé"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatPerfectTenseCanBeFormedWithEtreForPluralPerson() throws uk.co.mould.matt.exceptions.CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun =
                conjugator.getConjugationOf(
                        new InfinitiveVerb("aller", null, "être"),
                        Persons.FIRST_PERSON_PLURAL,
                        new PerfectIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Nous sommes allés"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatVerbWithTwoFormsForAPersonCanBeFormedCorrectly() throws CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugator.getConjugationOf(
                new InfinitiveVerb("pouvoir", null, null),
                Persons.FIRST_PERSON_PLURAL,
                new PresentIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Nous pouvons"), conjugatedVerbWithPronoun);
    }

    @Test
    public void testThatCanConjugatePerfectTense() throws CantConjugateException {
        ConjugatedVerbWithPronoun conjugatedVerbWithPronoun = conjugator.getConjugationOf(
                new InfinitiveVerb("pouvoir", null, "avoir"),
                Persons.FIRST_PERSON_PLURAL,
                new PerfectIndicative());
        assertEquals(new ConjugatedVerbWithPronoun("Nous avons pu"), conjugatedVerbWithPronoun);
    }
}
