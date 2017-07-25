package power.util;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

/**
 * Created by ronei.gebert on 25/07/2017.
 */
@Singleton
public class LangProducer {

    final Lang instance = new Lang.Builder().build();

    @Produces
    public Lang getLang(){
        return instance;
    }

}
