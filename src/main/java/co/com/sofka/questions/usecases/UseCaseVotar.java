package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Voto;
import co.com.sofka.questions.mapper.MapperUtil;
import co.com.sofka.questions.model.VotoDTO;
import co.com.sofka.questions.repositories.VotoRepository;
import co.com.sofka.questions.usecases.interfaces.SaveVoto;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class UseCaseVotar implements SaveVoto {

    private final VotoRepository votoRepository;
    private final MapperUtil mapperUtil;

    public UseCaseVotar(VotoRepository votoRepository, MapperUtil mapperUtil) {
        this.votoRepository = votoRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public Mono<String> apply(VotoDTO votoDTO) {
        Voto voto = mapperUtil.mapperToVoto().apply(votoDTO);
        return votoRepository.findByQuestionIdAndAnswerIdAndUserId(voto.getQuestionId(), voto.getAnswerId(), voto.getUserId())
                .flatMap(voto1 -> Mono.just("Ya voto"))
                .switchIfEmpty(guardar(voto));
    }

    public Mono<String> guardar(Voto voto) {
        return votoRepository.save(voto)
                    .map(Voto::getId);
    }
    
}
