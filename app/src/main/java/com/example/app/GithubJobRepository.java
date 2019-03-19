package com.example.app;

import java.util.List;


public class GithubJobRepository {
    public static final String TAG = GithubJobRepository.class.getSimpleName();

    public static io.reactivex.Observable<List<Position>> getPositions(String key, int offset) {
//        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
//        io.reactivex.Observable<List<Position>> positionListObservable = remoteDataSource.getPositionListObservable(key, offset);
        return null;
    }

}
