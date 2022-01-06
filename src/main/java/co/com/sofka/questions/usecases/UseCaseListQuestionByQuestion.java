package co.com.sofka.questions.usecases;

import co.com.sofka.questions.mapper.MapperUtil;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Service
@Validated
public class UseCaseListQuestionByQuestion implements Function<String, Flux<QuestionDTO>> {


    private final QuestionRepository questionRepository;
    private final MapperUtil mapper;

    public UseCaseListQuestionByQuestion(QuestionRepository questionRepository, MapperUtil mapper) {
        this.questionRepository = questionRepository;
        this.mapper = mapper;
    }

    @Override
    public Flux<QuestionDTO> apply(String question) {
        return questionRepository.findByQuestionContains(question)
                .map(mapper.mapEntityToQuestion());
    }
}
