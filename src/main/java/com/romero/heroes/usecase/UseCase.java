package com.romero.heroes.usecase;

public interface UseCase <T, K>{
    public T execute(K param);
}
