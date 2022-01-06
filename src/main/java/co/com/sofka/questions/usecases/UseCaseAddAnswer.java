package co.com.sofka.questions.usecases;

import co.com.sofka.questions.mapper.MapperUtil;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.usecases.interfaces.SaveAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UseCaseAddAnswer implements SaveAnswer {

    private final AnswerRepository answerRepository;
    private final MapperUtil mapper;
    private final UseCaseGet useCaseGet;

    @Autowired
    public UseCaseAddAnswer(MapperUtil mapper, UseCaseGet useCaseGet, AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
        this.useCaseGet = useCaseGet;
        this.mapper = mapper;
    }

    @Override
    public Mono<QuestionDTO> apply(AnswerDTO answerDTO) throws Exception {
        Objects.requireNonNull(answerDTO.getQuestionId(), "El QuestionId no puede ser nulo");
        isValidAnswerDTO(answerDTO);
        return useCaseGet.apply(answerDTO.getQuestionId())
                .flatMap(question -> answerRepository.save(mapper.mapperToAnswer().apply(answerDTO))
                        .map(answer -> {
                            question.getAnswers().add(answerDTO);
                            return question;
                        })
                );
    }

    private Mono<Void> isValidAnswerDTO(AnswerDTO answerDTO) throws Exception {
        int longitud = answerDTO.getAnswer().length();
        if (longitud < 15 && longitud > 250){
           throw new Exception("La respuesta debe tener entre 15 y 250 caracteres");
        }
        return null;
    }


}
