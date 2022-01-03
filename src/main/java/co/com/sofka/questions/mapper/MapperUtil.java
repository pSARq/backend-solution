package co.com.sofka.questions.mapper;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtil {

    public Function<AnswerDTO, Answer> mapperToAnswer(){
        return updateAnswer -> {
            Answer answer = new Answer();
            answer.setUserId(updateAnswer.getUserId());
            answer.setQuestionId(updateAnswer.getQuestionId());
            answer.setAnswer(updateAnswer.getAnswer());
            answer.setPosition(updateAnswer.getPosition());
            return answer;
        };
    }

    public Function<Answer, AnswerDTO> mapEntityToAnswer(){
        return entity -> new AnswerDTO(
                entity.getUserId(),
                entity.getQuestionId(),
                entity.getAnswer()
        );
    }

    public Function<QuestionDTO, Question> mapperToQuestion(String id){
        return updateQuestion -> {
            Question question = new Question();
            question.setId(id);
            question.setUserId(updateQuestion.getUserId());
            question.setQuestion(updateQuestion.getQuestion());
            question.setType(updateQuestion.getType());
            question.setCategory(updateQuestion.getCategory());
            return question;
        };
    }

    public Function<Question, QuestionDTO> mapEntityToQuestion(){
        return entity -> new QuestionDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getQuestion(),
                entity.getType(),
                entity.getCategory()
        );
    }
}
