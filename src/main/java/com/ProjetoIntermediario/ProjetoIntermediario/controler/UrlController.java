package com.ProjetoIntermediario.ProjetoIntermediario.controler;

import com.ProjetoIntermediario.ProjetoIntermediario.model.UrlRequest;
import com.ProjetoIntermediario.ProjetoIntermediario.model.Url;
import com.ProjetoIntermediario.ProjetoIntermediario.repository.urlRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/listaUrl")
public class UrlController {
    @Autowired
    private urlRepository urlRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/listaUrl")
    public List<Url> listaDados() {
        return urlRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody UrlRequest urlRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // Chamada à API externa
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.encurtador.dev/encurtamentos", urlRequest, String.class);

            // Verificar o status code da resposta
            if (response.getStatusCode() == HttpStatus.OK) {
                // Usar Jackson para analisar o JSON retornado
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());

                // Acessar a chave desejada do JSON
                String shortenedUrl = jsonResponse.get("urlEncurtada").asText();

                // Salvar no banco de dados
                LocalDateTime now = LocalDateTime.now();
                Url url = new Url(urlRequest.getUrl(), shortenedUrl, now);
                urlRepository.save(url);

                // Retornar apenas a URL encurtada como resposta
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            } else {
                // Se o status code não for OK, retornar um ResponseEntity com o status code da resposta da API externa
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            // Se ocorrer um erro de cliente (por exemplo, 400 Bad Request), tratar aqui
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (Exception e) {
            // Se ocorrer qualquer outra exceção, tratar aqui
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}