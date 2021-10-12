package by.milavitsky.horseracing.service.service_entity;


import by.milavitsky.horseracing.service.service_interface.ResultServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResultService implements ResultServiceInterface {

    private static final Logger logger = LogManager.getLogger(ResultService.class);

    private ResultService(){
    }


    private static class ResultServiceHolder{
        private static final ResultService HOLDER_INSTANCE = new ResultService();
    }

    public static ResultService getInstance() {
        return ResultServiceHolder.HOLDER_INSTANCE;
    }
}
