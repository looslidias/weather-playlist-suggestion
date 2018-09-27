package com.looslidias.playlistsuggestion.core.service.suggestion.genre;

import com.google.common.collect.Lists;
import com.looslidias.playlistsuggestion.core.properties.suggestion.SuggestionProperties;
import com.looslidias.playlistsuggestion.model.wheater.dto.WeatherDTO;
import lombok.extern.slf4j.Slf4j;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Slf4j
@Service
public class DroolsSuggestionService implements SuggestionGenreService {

    private static final String GENRES_GLOBAL = "genres";
    private KnowledgeBase knowledgeBase;

    @Autowired
    private SuggestionProperties suggestionProperties;

    @PostConstruct
    public void init() {
        knowledgeBase = readKnowledgeBase();
    }

    private KnowledgeBase readKnowledgeBase() {

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(suggestionProperties.getGenreRulesFile()), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();

        if (errors.size() > 0) {
            for (KnowledgeBuilderError error : errors) {
                log.error("DroolsSuggestionService [Knowledge parsing error] {}", error);
            }
            throw new IllegalArgumentException("Could not parse drools knowledge.");
        }

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        return kbase;
    }

    @Override
    public List<String> searchGenres(WeatherDTO weather) {
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
        session.setGlobal(GENRES_GLOBAL, Lists.newArrayList());

        session.insert(weather);
        session.fireAllRules();

        List<String> genres = (List<String>) session.getGlobal(GENRES_GLOBAL);
        session.dispose();

        return genres;
    }
}
