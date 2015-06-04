package uk.co.mould.matt.ui;

import uk.co.mould.matt.conjugators.Conjugator;
import uk.co.mould.matt.data.InfinitiveVerb;
import uk.co.mould.matt.data.Persons;
import uk.co.mould.matt.marking.AnswerChecker;
import uk.co.mould.matt.questions.QuestionGenerator;

public class QuestionPresenter {
	private final Conjugator conjugator;
	private AnswerChecker answerChecker;
	private QuestionView questionView;
	private QuestionGenerator fakeQuestionGenerator;
	private Persons.Person questionPerson;
	private InfinitiveVerb questionVerb;

	public QuestionPresenter(uk.co.mould.matt.ui.QuestionView questionView,
							 uk.co.mould.matt.questions.QuestionGenerator questionGenerator,
							 Conjugator conjugator) {
		this.questionView = questionView;
		this.fakeQuestionGenerator = questionGenerator;
		this.conjugator = conjugator;
		this.answerChecker = new AnswerChecker(conjugator);
	}

	public void showQuestion() {
		questionPerson = fakeQuestionGenerator.getRandomPerson();
		questionView.setPerson(questionPerson);
		questionVerb = fakeQuestionGenerator.getRandomVerb();
		questionView.setVerb(questionVerb);
		questionView.enterQuestionMode();
		questionView.hideCorrection();
		this.answerChecker.setQuestion(questionPerson, questionVerb);
	}

	public void submitAnswer() {
		answerChecker.check(questionView.getAnswer(), new AnswerChecker.Callback() {
			@Override
			public void correct() {
				questionView.setResultToCorrect();
			}

			@Override
			public void incorrect() {
				questionView.setResultToIncorrect();
				questionView.showCorrection();
				questionView.setCorrectAnswerValue(conjugator.getPresentConjugationOf(questionVerb, questionPerson));
			}
		});
		questionView.answerMode();
		questionView.enableAnswerBox();
		questionView.showResultBox();
	}

}
