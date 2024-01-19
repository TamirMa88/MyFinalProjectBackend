package tamir.ma.tamir.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import tamir.ma.tamir.security.JwtUtilities;

@Configuration
public class ShopConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public JwtUtilities jwtUtilities() {
        return new JwtUtilities();
    }

}
