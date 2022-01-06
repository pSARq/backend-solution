package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Voto;
import co.com.sofka.questions.mapper.MapperUtil;
import co.com.sofka.questions.model.VotoDTO;
import co.com.sofka.questions.repositories.VotoRepository;
import co.com.sofka.questions.usecases.interfaces.SaveVoto;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

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
        if (isEquals(votoDTO) == Mono.just("si")){
            return Mono.just("Ya realizó el voto");
        }else {
            return votoRepository.save(mapperUtil.mapperToVoto().apply(votoDTO))
                    .map(Voto::getId);
        }
    }

    public Mono<String> isEquals(VotoDTO votoDTO){
        Voto voto = new Voto();
        voto = mapperUtil.mapperToVoto().apply(votoDTO);
        if (votoRepository.equals(voto)){
            return Mono.just("si");
        }
        return Mono.just("si");
    }

/*
    @Override
    public Mono<VotoDTO> apply(VotoDTO votoDTO) {
        return exists(votoDTO)..map(voto -> votoDTO.equals(voto))
                .flatMap(voto -> votoRepository.save(mapperUtil.mapperToVoto().apply(votoDTO))
                        .map(voto1 -> mapperUtil.mapEntityToVoto().apply(voto1)));
    }

 */
}
