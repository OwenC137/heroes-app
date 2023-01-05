package com.romero.heroes.usecase;


public interface UseCase <T, K>{
    T execute(K param);
}
