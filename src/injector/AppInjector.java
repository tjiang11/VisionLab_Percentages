package injector;

import com.google.inject.AbstractModule;

import model.DotsPairGenerator;
import model.DotsPairGeneratorInterface;


public class AppInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(DotsPairGeneratorInterface.class).to(DotsPairGenerator.class);
    }
}
