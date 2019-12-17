package com.netreaders.dao._classes;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class KeyHolderBean {

    @Bean
    public KeyHolder getKeyHolder() {
        return new GeneratedKeyHolder();
    }
}
