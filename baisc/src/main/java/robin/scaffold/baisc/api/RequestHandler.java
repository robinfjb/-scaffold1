package robin.scaffold.baisc.api;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MediaType;

public interface RequestHandler {
    MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    Observable<String> request();
}
