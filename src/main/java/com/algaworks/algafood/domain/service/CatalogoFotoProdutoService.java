package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeArquivoExistente = null;
        String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }
        foto.setNomeArquivo(novoNomeArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();
        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .contentType(foto.getContentType())
                .inputStream(dadosArquivo)
                .build();
        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
        return foto;
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();
        fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
    }
}
