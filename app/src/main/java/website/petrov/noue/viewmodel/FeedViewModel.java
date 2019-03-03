package website.petrov.noue.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import website.petrov.noue.common.json.rpc.Request;
import website.petrov.noue.common.json.rpc.RequestBuilder;
import website.petrov.noue.common.json.rpc.Response;
import website.petrov.noue.common.viewmodel.BaseMutableGenericContextViewModel;
import website.petrov.noue.model.FeedModel;
import website.petrov.noue.model.request.DefaultRequest;
import website.petrov.noue.repository.data.StorageRepository;
import website.petrov.noue.repository.data.StorageShared;
import website.petrov.noue.repository.fetch.APIService;
import website.petrov.noue.repository.fetch.api.SyncAPI;
import website.petrov.noue.utilities.Provider;

import static website.petrov.noue.utilities.Constants.API.METHOD_FEED;

public final class FeedViewModel extends BaseMutableGenericContextViewModel<FeedModel> {
    private Disposable subscription;

    public FeedViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void load() {
        final StorageRepository repo = StorageRepository.getInstance();
        final List<FeedModel> list = repo.getFeed().getValue();
        if (list != null) {
            this.models.setValue(list);
        }

        final SyncAPI api = APIService.getInstance().create(SyncAPI.class);
        final Request request = new RequestBuilder()
                .withMethod(METHOD_FEED)
                .withParams(new Object[]{
                        new DefaultRequest(Provider.getDeviceId(getApplication()),
                                StorageShared.getInstanceId())
                })
                .build();
        final Observable<Response> feed = api.load(request);
        subscription = feed.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedResponse -> {

                }, error -> {
                    Log.d("FETCH-FAIL", error.getMessage());
                });
    }

    @Override
    protected void onCleared() {
        if (subscription != null) {
            subscription.dispose();
        }
    }
}
