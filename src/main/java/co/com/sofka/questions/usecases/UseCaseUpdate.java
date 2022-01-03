package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.mapper.MapperUtil;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.usecases.interfaces.SaveQuestion;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UseCaseUpdate implements SaveQuestion {

    private final QuestionRepository questionRepository;
    private final MapperUtil mapper;

    public UseCaseUpdate(QuestionRepository questionRepository, MapperUtil mapper) {
        this.questionRepository = questionRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<String> apply(QuestionDTO questionDTO) {
        Objects.requireNonNull(questionDTO.getId(), "questionId no puede ser nulo");
        return questionRepository
                .save(mapper.mapperToQuestion(questionDTO.getId()).apply(questionDTO))
                .map(Question::getId);
    }
}
